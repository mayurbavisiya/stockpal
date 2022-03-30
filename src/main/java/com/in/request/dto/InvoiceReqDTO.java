package com.in.request.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InvoiceReqDTO {

	private String invoiceNumber;
	private Integer vendorId;
	private Integer orderId;
	private String invoiceDate;
	private String dueDate;
	private String invoicePhotoUrl;

	@JsonProperty("invoiceProducts")
	private List<InvoiceProductReqDTO> invoiceProducts;

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public String getInvoicePhotoUrl() {
		return invoicePhotoUrl;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public void setInvoicePhotoUrl(String invoicePhotoUrl) {
		this.invoicePhotoUrl = invoicePhotoUrl;
	}

	public List<InvoiceProductReqDTO> getInvoiceProducts() {
		return invoiceProducts;
	}

	public void setInvoiceProducts(List<InvoiceProductReqDTO> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

}
