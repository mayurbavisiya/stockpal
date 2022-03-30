package com.in.request.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderReqDTO {

	private Integer vendorId;
	private String orderDate;

	@JsonProperty("orderProducts")
	private List<OrderProductReqDTO> orderProducts;

	public Integer getVendorId() {
		return vendorId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public List<OrderProductReqDTO> getOrderProducts() {
		return orderProducts;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public void setOrderProducts(List<OrderProductReqDTO> orderProducts) {
		this.orderProducts = orderProducts;
	}

}
