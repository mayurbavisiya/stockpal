package com.in.restapis;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in.bean.APIResponse;
import com.in.excel.OrderExcelExporter;
import com.in.exception.ValidationException;
import com.in.request.dto.OrderReqDTO;
import com.in.service.OrderService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OrderAPI {

	@Autowired
	OrderService orderService;

	@PostMapping("/order")
	public ResponseEntity<APIResponse> saveOrder(@RequestBody OrderReqDTO dto) throws ValidationException {
		return ResponseEntity.ok().body(orderService.saveOrder(dto));
	}

	@GetMapping("/orders")
	public ResponseEntity<APIResponse> getAllOrders(Pageable pageable) throws ValidationException {
		return ResponseEntity.ok().body(orderService.getAllOrders(pageable));
	}

	@GetMapping("/order/{orderId}")
	public ResponseEntity<APIResponse> getOrderById(@PathVariable(value = "orderId") Long orderId)
			throws ValidationException {
		return ResponseEntity.ok().body(orderService.getOrderById(orderId));
	}

	@GetMapping("/orders/{vendorId}")
	public ResponseEntity<APIResponse> getAllOrdersByVendor(@PathVariable(value = "vendorId") Long vendorId,
			Pageable pageable) throws ValidationException {
		return ResponseEntity.ok().body(orderService.getAllOrdersByVendor(vendorId, pageable));
	}

	@GetMapping("/products/{vendorId}")
	public ResponseEntity<APIResponse> getAllProductsByVendor(@PathVariable(value = "vendorId") Long vendorId,
			Pageable pageable) throws ValidationException {
		return ResponseEntity.ok().body(orderService.getAllProductsByVendor(vendorId, pageable));
	}

	@PutMapping("/order/{id}")
	public ResponseEntity<APIResponse> updateOrder(@PathVariable(value = "id") Long id, @RequestBody OrderReqDTO dto)
			throws ValidationException {
		return ResponseEntity.ok().body(orderService.updateOrder(id, dto));
	}

	@DeleteMapping("/order/{id}")
	public ResponseEntity<APIResponse> deleteOrder(@PathVariable(value = "id") Long id) throws ValidationException {
		return ResponseEntity.ok().body(orderService.deleteOrder(id));
	}

	@GetMapping("/orderDetails/{orderId}")
	public ResponseEntity<APIResponse> getOrderDetailsByOrderId(HttpServletResponse response,
			@PathVariable(value = "orderId") Long orderId, Pageable pageable)
			throws ValidationException, IOException {
		return ResponseEntity.ok().body(orderService.getOrderDetailsByOrderId(orderId, pageable));
	}

	@GetMapping("/orderDetails/excel/{orderId}")
	public ResponseEntity<APIResponse> getOrderDetailsExcelByOrderId(HttpServletResponse response,
			@PathVariable(value = "orderId") Long orderId, Pageable pageable) throws ValidationException, IOException {
		exportReport(response, orderId, pageable);
		return null;
	}

	public void exportReport(HttpServletResponse response, Long orderId, Pageable pageable) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=OrderId_" + orderId + ".xlsx";
		response.setHeader(headerKey, headerValue);
		try {
			APIResponse orderDetailsRes = orderService.getOrderDetailsByOrderId(orderId, pageable);
			OrderExcelExporter excelExporter = new OrderExcelExporter(orderDetailsRes.getOrderDetailsByOrderId());
			excelExporter.export(response);
		} catch (Exception e) {
			OrderExcelExporter excelExporter = new OrderExcelExporter(null);
			excelExporter.export(response);
		}
	}

}
