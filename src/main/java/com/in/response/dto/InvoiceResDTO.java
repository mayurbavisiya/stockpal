package com.in.response.dto;

import java.util.List;

public class InvoiceResDTO {

	private Long id;
	private String invoiceNumber;
	private Integer vendorId;
	private String vendorName;
	private Integer orderId;
	private String invoiceDate;
	private String dueDate;
	private String invoicePhotoUrl;

	private List<InvoiceProductResDTO> invoiceProducts;

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

	public List<InvoiceProductResDTO> getInvoiceProducts() {
		return invoiceProducts;
	}

	public void setInvoiceProducts(List<InvoiceProductResDTO> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	
}
