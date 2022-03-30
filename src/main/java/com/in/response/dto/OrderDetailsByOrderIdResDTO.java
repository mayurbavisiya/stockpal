package com.in.response.dto;

public class OrderDetailsByOrderIdResDTO {

	private Integer orderId;
	private Integer vendorId;
	private String vendorName;
	private String vendorContactPersonName;
	private String vendorMobile;
	private String vendorAddress;
	private String vendorEmail;
	private String vendorFax;
	private Integer productId;
	private String productName;
	private Integer qty;
	private String orderDate;
	private Double productPrice;

	private Double cost;
	private Double margin;
	private Double sellingPrice;

	public Integer getOrderId() {
		return orderId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public String getVendorContactPersonName() {
		return vendorContactPersonName;
	}

	public String getVendorMobile() {
		return vendorMobile;
	}

	public String getVendorAddress() {
		return vendorAddress;
	}

	public String getVendorEmail() {
		return vendorEmail;
	}

	public String getVendorFax() {
		return vendorFax;
	}

	public Integer getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public Integer getQty() {
		return qty;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public void setVendorContactPersonName(String vendorContactPersonName) {
		this.vendorContactPersonName = vendorContactPersonName;
	}

	public void setVendorMobile(String vendorMobile) {
		this.vendorMobile = vendorMobile;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public void setVendorEmail(String vendorEmail) {
		this.vendorEmail = vendorEmail;
	}

	public void setVendorFax(String vendorFax) {
		this.vendorFax = vendorFax;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Double getCost() {
		return cost;
	}

	public Double getMargin() {
		return margin;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

}