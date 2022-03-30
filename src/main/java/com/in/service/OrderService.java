package com.in.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.request.dto.OrderReqDTO;

@Service
public interface OrderService {

	APIResponse saveOrder(OrderReqDTO dto) throws ValidationException;

	APIResponse getAllOrders(Pageable pageable);

	APIResponse deleteOrder(Long id) throws ValidationException;

	APIResponse getUpcomingOrderScheduled();

	APIResponse updateOrder(Long id, OrderReqDTO dto) throws ValidationException;

	APIResponse getAllOrdersByVendor(Long vendorId, Pageable pageable) throws ValidationException;

	APIResponse getOrderById(Long orderId) throws ValidationException;

	APIResponse getAllProductsByVendor(Long vendorId, Pageable pageable) throws ValidationException;

	APIResponse getOrderDetailsByOrderId(Long orderId, Pageable pageable) throws ValidationException;

}
