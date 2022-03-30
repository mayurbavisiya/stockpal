package com.in.response.dto;

import java.util.List;

public class OrderResDTO {

	private Integer id;
	private Integer vendorId;
	private String vendorName;
	private String orderDate;

	private List<OrderProductResDTO> orderProducts;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public List<OrderProductResDTO> getOrderProducts() {
		return orderProducts;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public void setOrderProducts(List<OrderProductResDTO> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

}
