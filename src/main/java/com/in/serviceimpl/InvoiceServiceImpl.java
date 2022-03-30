package com.in.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.entity.InvoiceEntity;
import com.in.entity.InvoiceProductEntity;
import com.in.entity.ProductEntity;
import com.in.entity.VendorEntity;
import com.in.exception.ValidationException;
import com.in.repository.InvoiceRepository;
import com.in.repository.ProductRepository;
import com.in.repository.VendorRepository;
import com.in.request.dto.InvoiceProductReqDTO;
import com.in.request.dto.InvoiceReqDTO;
import com.in.response.dto.InvoiceProductResDTO;
import com.in.response.dto.InvoiceResDTO;
import com.in.service.InvoiceService;
import com.in.util.Utility;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	InvoiceRepository invoiceRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	VendorRepository vendorRepository;

	@Override
	public APIResponse saveInvoice(InvoiceReqDTO dto) throws ValidationException {

		List<InvoiceEntity> invoiceList = invoiceRepository.getInvoiceByNumber(dto.getInvoiceNumber());
		if (invoiceList.size() > 0)
			throw new ValidationException("dataAlreadyExistCode", "InvoiceAlreadyExist", "successBooleanFalse");
		if(dto.getVendorId() == null)
			throw new ValidationException("dataNotFoundCode", "vendorNotFoundDesc", "successBooleanFalse");
		InvoiceEntity invoice = new InvoiceEntity();
		BeanUtils.copyProperties(dto, invoice);
		VendorEntity vendor = vendorRepository.findById(Long.valueOf(dto.getVendorId())).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "vendorNotFoundDesc", "successBooleanFalse"));
		if (vendor != null)
			invoice.setVendor(vendor);
		invoice.setInvoiceDate(Utility.getDateFromString(dto.getInvoiceDate()));
		invoice.setCreatedDate(new Date());
		invoice.setDueDate(Utility.getDateFromString(dto.getDueDate()));
		invoice.setIsActive(1);

		Set<InvoiceProductEntity> invoiceProductSet = new HashSet<>();
		for (InvoiceProductReqDTO obj : dto.getInvoiceProducts()) {
			InvoiceProductEntity invoiceProductEn = new InvoiceProductEntity();
			BeanUtils.copyProperties(obj, invoiceProductEn);
			ProductEntity product = productRepository.findById(Long.valueOf(obj.getProductId())).orElseThrow(
					() -> new ValidationException("dataNotFoundCode", "ProductNotFoundDesc", "successBooleanFalse"));
			if (product != null)
				invoiceProductEn.setProduct(product);
			// Here we need to update costPrice for the respective product into
			// product_price table
			updateCostPriceForProduct(obj.getProductId(), obj.getCostPrice());
			invoiceProductSet.add(invoiceProductEn);
		}

		invoice.setInvoiceProductEntity(invoiceProductSet);
		invoiceRepository.save(invoice);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	private void updateCostPriceForProduct(Integer productId, Double costPrice) {
		productRepository.updateProductPriceByProductId(productId, costPrice);
	}

	@Override
	public APIResponse deleteInvoice(Long id) throws ValidationException {
		InvoiceEntity invoice = invoiceRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "InvoiceNotFoundDesc", "successBooleanFalse"));

		invoice.setIsActive(0);
		// Soft delete
		invoiceRepository.save(invoice);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;

	}

	@Override
	public APIResponse getAllInvoice(Pageable pageable, String fromDate, String toDate, String invoiceId) {

		fromDate = (fromDate != null && !fromDate.isEmpty()) ? fromDate + " 00:00:00" : null;
		toDate = (toDate != null && !toDate.isEmpty()) ? toDate + " 23:59:59" : null;
		Page<InvoiceEntity> invoicePages = invoiceRepository.findAll(pageable, fromDate, toDate, invoiceId);
		List<InvoiceEntity> enList = invoicePages.getContent();
		Integer totalPages = invoicePages.getTotalPages();
		Integer pageRecords = invoicePages.getNumberOfElements();
		Integer totalRecords = (int) invoicePages.getTotalElements();

		List<InvoiceResDTO> invoiceList = enList.stream().map(x -> {
			InvoiceResDTO dto = new InvoiceResDTO();
			BeanUtils.copyProperties(x, dto);
			dto.setVendorId(x.getVendor().getId().intValue());
			dto.setVendorName(x.getVendor().getName());
			dto.setInvoiceDate(Utility.getStringFromDate(x.getInvoiceDate(), false));
			dto.setDueDate(Utility.getStringFromDate(x.getDueDate(), false));
			List<InvoiceProductResDTO> invoiceProducts = new ArrayList<>();
			for (InvoiceProductEntity productEn : x.getInvoiceProductEntity()) {
				InvoiceProductResDTO productDTO = new InvoiceProductResDTO();
				BeanUtils.copyProperties(productEn, productDTO);
				productDTO.setProductName(productEn.getProduct().getName());
				productDTO.setProductId(productEn.getProduct().getId().intValue());
				invoiceProducts.add(productDTO);
			}
			dto.setInvoiceProducts(invoiceProducts);
			return dto;
		}).collect(Collectors.toList());

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		if (invoiceList == null || invoiceList.isEmpty())
			response.setCommonMessage("We don't have invoice data to show here.");
		response.setInvoices(invoiceList);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;

	}

	@Override
	public APIResponse getInvoiceById(Long id) throws ValidationException {

		InvoiceEntity invoice = invoiceRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "InvoiceNotFoundDesc", "successBooleanFalse"));

		List<Object[]> objList = invoiceRepository.findInvoiceById(id);
		InvoiceResDTO dto = new InvoiceResDTO();
		BeanUtils.copyProperties(invoice, dto);
		dto.setInvoiceDate(Utility.getStringFromDate(invoice.getInvoiceDate(), false));
		dto.setDueDate(Utility.getStringFromDate(invoice.getDueDate(), false));
		List<InvoiceProductResDTO> invoiceProducts = new ArrayList<>();

		for (Object[] obj : objList) {
			dto.setId(obj[0] == null ? 0 : Long.valueOf(String.valueOf(obj[0])));
			dto.setInvoiceNumber(obj[1] == null ? "" : (String.valueOf(obj[1])));
			dto.setVendorId(obj[2] == null ? 0 : Integer.valueOf(String.valueOf(obj[2])));
			dto.setVendorName(obj[3] == null ? "" : (String.valueOf(obj[3])));
			dto.setOrderId(obj[4] == null ? 0 : Integer.valueOf(String.valueOf(obj[4])));
			dto.setInvoiceDate(obj[5] == null ? "" : (String.valueOf(obj[5])));
			dto.setDueDate(obj[6] == null ? "" : (String.valueOf(obj[6])));
			dto.setInvoicePhotoUrl(obj[7] == null ? "" : (String.valueOf(obj[7])));
			InvoiceProductResDTO product = new InvoiceProductResDTO();
			product.setProductId(obj[8] == null ? 0 : Integer.valueOf(String.valueOf(obj[8])));
			product.setProductName(obj[9] == null ? "" : (String.valueOf(obj[9])));
			product.setReceivedQty(obj[10] == null ? 0 : Integer.valueOf(String.valueOf(obj[10])));
			product.setCostPrice(obj[11] == null ? 0.0 : Double.valueOf(String.valueOf(obj[11])));
			product.setMargin(obj[12] == null ? 0.0 : Double.valueOf(String.valueOf(obj[12])));
			product.setSellingPrice(obj[13] == null ? 0.0 : Double.valueOf(String.valueOf(obj[13])));
			invoiceProducts.add(product);
		}
		dto.setInvoiceProducts(invoiceProducts);
		List<InvoiceResDTO> invoiceList = new ArrayList<>();
		invoiceList.add(dto);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		if (invoiceList == null || invoiceList.isEmpty())
			response.setCommonMessage("We don't have invoice data to show here.");
		response.setInvoices(invoiceList);
		return response;
	}

}
