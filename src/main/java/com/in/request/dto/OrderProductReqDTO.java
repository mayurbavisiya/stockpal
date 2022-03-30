package com.in.request.dto;

public class OrderProductReqDTO {
	private Integer productId;
	private Integer reorderQty;
	private Integer suggestedReOrderQty;

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

}
