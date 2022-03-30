package com.in.response.dto;

public class InvoiceProductResDTO {

	private Integer productId;
	private String productName;
	private Integer receivedQty;
	private Double costPrice;
	private Double margin;
	private Double sellingPrice;

	public Integer getProductId() {
		return productId;
	}

	public Integer getReceivedQty() {
		return receivedQty;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public Double getMargin() {
		return margin;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setReceivedQty(Integer receivedQty) {
		this.receivedQty = receivedQty;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
