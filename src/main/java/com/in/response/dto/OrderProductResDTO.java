package com.in.response.dto;

public class OrderProductResDTO {

	private Integer id;
	private Integer productId;
	private String productName;
	private Integer reorderQty;
	private Integer suggestedReOrderQty;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public Integer getReorderQty() {
		return reorderQty;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setReorderQty(Integer reorderQty) {
		this.reorderQty = reorderQty;
	}

	public Integer getSuggestedReOrderQty() {
		return suggestedReOrderQty;
	}

	public void setSuggestedReOrderQty(Integer suggestedReOrderQty) {
		this.suggestedReOrderQty = suggestedReOrderQty;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
