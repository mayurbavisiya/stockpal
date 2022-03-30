package com.in.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.entity.NotificationEntity;
import com.in.entity.OrderEntity;
import com.in.entity.OrderProductEntity;
import com.in.entity.ProductEntity;
import com.in.entity.VendorEntity;
import com.in.exception.ValidationException;
import com.in.repository.NotificationRepository;
import com.in.repository.OrderRepository;
import com.in.repository.ProductRepository;
import com.in.repository.VendorRepository;
import com.in.request.dto.OrderProductReqDTO;
import com.in.request.dto.OrderReqDTO;
import com.in.response.dto.OrderDetailsByOrderIdResDTO;
import com.in.response.dto.OrderProductByVendorsResDTO;
import com.in.response.dto.OrderProductResDTO;
import com.in.response.dto.OrderResDTO;
import com.in.response.dto.UpcomingScheduledOrderResDTO;
import com.in.service.OrderService;
import com.in.util.Utility;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	PushNotificationServiceImpl pushNotification;

	@Override
	public APIResponse saveOrder(OrderReqDTO dto) throws ValidationException {

		OrderEntity orderEn = new OrderEntity();
		orderEn.setOrderDate(Utility.getDateFromString(dto.getOrderDate()));

		VendorEntity vendor = vendorRepository.findById(Long.valueOf(dto.getVendorId())).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "vendorNotFoundDesc", "successBooleanFalse"));
		if (vendor != null)
			orderEn.setVendor(vendor);

		Set<OrderProductEntity> orderProducts = new HashSet<>();
		for (OrderProductReqDTO orderProdDTO : dto.getOrderProducts()) {
			OrderProductEntity orderProdEn = new OrderProductEntity();
			BeanUtils.copyProperties(orderProdDTO, orderProdEn);
			ProductEntity product = productRepository.findById(Long.valueOf(orderProdDTO.getProductId())).orElseThrow(
					() -> new ValidationException("dataNotFoundCode", "ProductNotFoundDesc", "successBooleanFalse"));
			if (product != null)
				orderProdEn.setProduct(product);
			orderProdEn.setSuggestedReOrderQty(orderProdDTO.getSuggestedReOrderQty());
			orderProducts.add(orderProdEn);
		}
		orderEn.setOrderProducts(orderProducts);

		orderRepository.save(orderEn);

		// Save into Notifications
		saveOrderRecordsInNotification(dto, orderEn.getId().intValue());

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	private void saveOrderRecordsInNotification(OrderReqDTO dto, int orderId) {
		NotificationEntity notification = new NotificationEntity();
		notification.setCreatedDate(new Date());

		notification.setNotificationType(Integer.parseInt(Utility.getFCMProperty("new.order.received.noti.type")));
		notification.setTitle(Utility.getFCMProperty("new.order.received.noti.title"));

		List<Object[]> notificationDescArr = orderRepository.getProductsByOrderId(orderId);
		String notificationDesc = "";
		if (notificationDescArr != null && notificationDescArr.size() > 0) {
			if (notificationDescArr.get(0)[0] != null) {
				notificationDesc = String.valueOf(notificationDescArr.get(0)[0]);
			}
		}
		notification.setNotificationDesc(notificationDesc);
		notificationRepository.save(notification);

		// Push Notification
		pushNotification.pushNotification(
				Integer.parseInt(String.valueOf(Utility.getFCMProperty("new.order.received.noti.type"))),
				String.valueOf(Utility.getFCMProperty("new.order.received.noti.title")), notificationDesc);

	}

	@Override
	public APIResponse getAllOrders(Pageable pageable) {

		Page<OrderEntity> orderPages = orderRepository.findAll(pageable);
		List<OrderEntity> enList = orderPages.getContent();
		Integer totalPages = orderPages.getTotalPages();
		Integer pageRecords = orderPages.getNumberOfElements();
		Integer totalRecords = (int) orderPages.getTotalElements();

		List<OrderResDTO> orderList = enList.stream().map(x -> {
			OrderResDTO dto = new OrderResDTO();
			dto.setId(x.getId().intValue());
			dto.setOrderDate(Utility.getStringFromDate(x.getOrderDate(), false));
			dto.setVendorName(x.getVendor().getName());
			dto.setVendorId(x.getVendor().getId().intValue());

			List<OrderProductResDTO> orderProducts = new ArrayList<>();
			for (OrderProductEntity proEn : x.getOrderProducts()) {
				OrderProductResDTO proDTO = new OrderProductResDTO();
				proDTO.setId(proEn.getId().intValue());
				proDTO.setProductId(proEn.getProduct().getId().intValue());
				proDTO.setProductName(proEn.getProduct().getName());
				proDTO.setReorderQty(proEn.getReorderQty());
				proDTO.setSuggestedReOrderQty(proEn.getSuggestedReOrderQty());
				orderProducts.add(proDTO);
			}
			dto.setOrderProducts(orderProducts);
			return dto;
		}).collect(Collectors.toList());

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setOrders(orderList);
		if (orderList == null || orderList.isEmpty())
			response.setCommonMessage("We dont have any orders to show");
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;

	}

	@Override
	public APIResponse deleteOrder(Long id) throws ValidationException {

		OrderEntity order = orderRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "orderNotFoundDesc", "successBooleanFalse"));

		orderRepository.delete(order);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;

	}

	@Override
	public APIResponse getUpcomingOrderScheduled() {
		List<Object[]> list = vendorRepository.getScheduledVendors();
		HashMap<String, List<UpcomingScheduledOrderResDTO>> hm = new LinkedHashMap<String, List<UpcomingScheduledOrderResDTO>>();
		hm = initMapWithWeekDays(hm);
		for (Object[] obj : list) {
			UpcomingScheduledOrderResDTO dto = new UpcomingScheduledOrderResDTO();
			dto.setEmail(obj[5] != null ? String.valueOf(obj[5]) : "");
			dto.setFax(obj[4] != null ? String.valueOf(obj[4]) : "");
			dto.setVendorId(obj[1] != null ? String.valueOf(obj[1]) : "");
			dto.setVendorName(obj[2] != null ? String.valueOf(obj[2]) : "");
			hm.computeIfAbsent(String.valueOf(obj[0]), k -> new ArrayList<UpcomingScheduledOrderResDTO>()).add(dto);
		}

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setScheduledOrders(hm);
		return response;
	}

	private HashMap<String, List<UpcomingScheduledOrderResDTO>> initMapWithWeekDays(
			HashMap<String, List<UpcomingScheduledOrderResDTO>> hm) {
		hm.put("Monday", new ArrayList<UpcomingScheduledOrderResDTO>());
		hm.put("Tuesday", new ArrayList<UpcomingScheduledOrderResDTO>());
		hm.put("Wednesday", new ArrayList<UpcomingScheduledOrderResDTO>());
		hm.put("Thursday", new ArrayList<UpcomingScheduledOrderResDTO>());
		hm.put("Friday", new ArrayList<UpcomingScheduledOrderResDTO>());
		hm.put("Saturday", new ArrayList<UpcomingScheduledOrderResDTO>());
		hm.put("Sunday", new ArrayList<UpcomingScheduledOrderResDTO>());
		return hm;
	}

	@Override
	public APIResponse updateOrder(Long id, OrderReqDTO dto) throws ValidationException {
		OrderEntity order = orderRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "orderNotFoundDesc", "successBooleanFalse"));

		if (dto.getOrderDate() != null)
			order.setOrderDate(Utility.getDateFromString(dto.getOrderDate()));

		if (dto.getVendorId() != null) {
			// order.setVendorId(dto.getVendorId());
			VendorEntity vendor = vendorRepository.findById(Long.valueOf(dto.getVendorId())).orElseThrow(
					() -> new ValidationException("dataNotFoundCode", "vendorNotFoundDesc", "successBooleanFalse"));
			if (vendor != null)
				order.setVendor(vendor);
		}

		if (dto.getOrderProducts() != null) {
			Set<OrderProductEntity> orderProducts = new HashSet<>();
			for (OrderProductReqDTO orderProdDTO : dto.getOrderProducts()) {
				OrderProductEntity orderProdEn = new OrderProductEntity();
				BeanUtils.copyProperties(orderProdDTO, orderProdEn);
				if (orderProdDTO.getProductId() != null) {
					ProductEntity product = productRepository.findById(Long.valueOf(orderProdDTO.getProductId()))
							.orElseThrow(() -> new ValidationException("dataNotFoundCode", "ProductNotFoundDesc",
									"successBooleanFalse"));
					if (product != null)
						orderProdEn.setProduct(product);
				}
				orderProdEn.setSuggestedReOrderQty(orderProdDTO.getSuggestedReOrderQty());
				orderProducts.add(orderProdEn);
			}
			if (orderProducts.size() > 0) {
				// Delete Existing Order Products
				orderRepository.deleteOrderProductsByOrderId(id.intValue());
			}
			order.setOrderProducts(orderProducts);
		}
		orderRepository.save(order);

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;

	}

	@Override
	public APIResponse getAllOrdersByVendor(Long vendorId, Pageable pageable) throws ValidationException {

		Page<OrderEntity> orderPages = orderRepository.getOrdersByVendor(pageable, vendorId.intValue());
		List<OrderEntity> enList = orderPages.getContent();
		Integer totalPages = orderPages.getTotalPages();
		Integer pageRecords = orderPages.getNumberOfElements();
		Integer totalRecords = (int) orderPages.getTotalElements();

		List<OrderResDTO> orderList = enList.stream().map(x -> {
			OrderResDTO dto = new OrderResDTO();
			dto.setId(x.getId().intValue());
			dto.setOrderDate(Utility.getStringFromDate(x.getOrderDate(), false));
			dto.setVendorId(x.getVendor().getId().intValue());
			dto.setVendorName(x.getVendor().getName());
			List<OrderProductResDTO> orderProducts = new ArrayList<>();
			for (OrderProductEntity proEn : x.getOrderProducts()) {
				OrderProductResDTO proDTO = new OrderProductResDTO();
				proDTO.setId(proEn.getId().intValue());
				proDTO.setProductId(proEn.getProduct().getId().intValue());
				proDTO.setProductName(proEn.getProduct().getName());
				proDTO.setReorderQty(proEn.getReorderQty());
				proDTO.setSuggestedReOrderQty(proEn.getSuggestedReOrderQty());
				orderProducts.add(proDTO);
			}
			dto.setOrderProducts(orderProducts);
			return dto;
		}).collect(Collectors.toList());

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setOrders(orderList);
		if (orderList == null || orderList.isEmpty())
			response.setCommonMessage("We dont have any orders to show");
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;
	}

	@Override
	public APIResponse getOrderById(Long orderId) throws ValidationException {

		List<Object[]> ordersList = orderRepository.getOrderByOrderId(orderId);
		if (ordersList == null || ordersList.size() < 1)
			throw new ValidationException("dataNotFoundCode", "orderNotFoundDesc", "successBooleanFalse");

		List<OrderProductResDTO> orderProducts = new ArrayList<>();
		OrderResDTO dto = new OrderResDTO();
		for (Object[] obj : ordersList) {

			dto.setId(obj[0] == null ? 0 : Integer.valueOf(String.valueOf(obj[0])));
			dto.setVendorId(obj[1] == null ? 0 : Integer.valueOf(String.valueOf(obj[1])));
			dto.setOrderDate(obj[2] == null ? "" : (String.valueOf(obj[2])));
			OrderProductResDTO proDTO = new OrderProductResDTO();
			proDTO.setId(obj[3] == null ? 0 : Integer.valueOf(String.valueOf(obj[3])));
			proDTO.setProductId(obj[4] == null ? 0 : Integer.valueOf(String.valueOf(obj[4])));
			proDTO.setReorderQty(obj[5] == null ? 0 : Integer.valueOf(String.valueOf(obj[5])));
			proDTO.setSuggestedReOrderQty(obj[6] == null ? 0 : Integer.valueOf(String.valueOf(obj[6])));
			proDTO.setProductName(obj[7] == null ? "" : (String.valueOf(obj[7])));
			orderProducts.add(proDTO);
		}
		dto.setOrderProducts(orderProducts);

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setOrder(dto);
		return response;

	}

	@Override
	public APIResponse getAllProductsByVendor(Long vendorId, Pageable pageable) throws ValidationException {
		// Vendor id is valid or not
		vendorRepository.findById(vendorId).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "vendorNotFoundDesc", "successBooleanFalse"));

		List<Object[]> productData = orderRepository.getAllProductsByVendor(vendorId.intValue());
		List<OrderProductByVendorsResDTO> responseData = new ArrayList<OrderProductByVendorsResDTO>();
		if (!productData.isEmpty()) {
			for (Object[] obj : productData) {
				OrderProductByVendorsResDTO dto = new OrderProductByVendorsResDTO();
				dto.setProductId(obj[0] != null ? Integer.parseInt(String.valueOf(obj[0])) : 0);
				dto.setProductName(obj[1] != null ? String.valueOf(obj[1]) : "");
				dto.setLastOrderDate(obj[2] != null ? String.valueOf(obj[2]) : "");
				dto.setQtyOrdered(obj[3] != null ? Integer.parseInt(String.valueOf(obj[3])) : 0);
				dto.setQtySold30Days(obj[4] != null ? Integer.parseInt(String.valueOf(obj[4])) : 0);
				dto.setQtySold7Days(obj[5] != null ? Integer.parseInt(String.valueOf(obj[5])) : 0);
				dto.setInStock(obj[6] != null ? Integer.parseInt(String.valueOf(obj[6])) : 0);
				dto.setStockForeCast("2 days");
				dto.setReOrderQty(obj[3] != null ? Integer.parseInt(String.valueOf(obj[3])) : 0);
				dto.setSuggestedOrderQty(obj[8] != null ? Integer.parseInt(String.valueOf(obj[8])) : 0);
				dto.setCostPrice(obj[9] != null ? Double.valueOf(String.valueOf(obj[9])) : 0.0);
				dto.setMargin(obj[10] != null ? Double.valueOf(String.valueOf(obj[10])) : 0.0);
				dto.setSellingPrice(obj[11] != null ? Double.valueOf(String.valueOf(obj[11])) : 0.0);
				responseData.add(dto);
			}
		}
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setProductByVendors(responseData);
		return response;
	}

	@Override
	public APIResponse getOrderDetailsByOrderId(Long orderId, Pageable pageable) throws ValidationException {

		// Vendor id is valid or not
		orderRepository.findById(orderId).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "orderNotFoundDesc", "successBooleanFalse"));

		List<Object[]> orderDetails = orderRepository.getOrderDetailsByOrderId(orderId.intValue());
		List<OrderDetailsByOrderIdResDTO> responseData = new ArrayList<OrderDetailsByOrderIdResDTO>();

		if (!orderDetails.isEmpty()) {
			for (Object[] obj : orderDetails) {
				OrderDetailsByOrderIdResDTO dto = new OrderDetailsByOrderIdResDTO();
				dto.setOrderId(obj[0] != null ? Integer.parseInt(String.valueOf(obj[0])) : 0);
				dto.setVendorId(obj[1] != null ? Integer.parseInt(String.valueOf(obj[1])) : 0);
				dto.setVendorName(obj[2] != null ? String.valueOf(obj[2]) : "");
				dto.setVendorContactPersonName(obj[3] != null ? String.valueOf(obj[3]) : "");
				dto.setVendorMobile(obj[4] != null ? String.valueOf(obj[4]) : "");
				dto.setVendorAddress(obj[5] != null ? String.valueOf(obj[5]) : "");
				dto.setVendorEmail(obj[6] != null ? String.valueOf(obj[6]) : "");
				dto.setVendorFax(obj[7] != null ? String.valueOf(obj[7]) : "");
				dto.setProductId(obj[8] != null ? Integer.parseInt(String.valueOf(obj[8])) : 0);
				dto.setProductName(obj[9] != null ? String.valueOf(obj[9]) : "");
				dto.setQty(obj[10] != null ? Integer.parseInt(String.valueOf(obj[10])) : 0);
				dto.setOrderDate(obj[11] != null ? String.valueOf(obj[11]) : "");
				dto.setProductPrice(obj[12] != null ? Double.parseDouble(String.valueOf(obj[12])) : 0.0);
				dto.setCost(obj[12] != null ? Double.valueOf(String.valueOf(obj[12])) : 0.0);
				dto.setMargin(obj[13] != null ? Double.valueOf(String.valueOf(obj[13])) : 0.0);
				dto.setSellingPrice(obj[14] != null ? Double.valueOf(String.valueOf(obj[14])) : 0.0);
				responseData.add(dto);
			}
		}
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setOrderDetailsByOrderId(responseData);
		return response;

	}

}
