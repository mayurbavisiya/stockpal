package com.in.request.dto;

public class StockAdjustmentReqDTO {

	private Long productId;
	private Integer adjustedStock;
	private Boolean add;
	private Boolean remove;
	private String reason;

	public Long getProductId() {
		return productId;
	}

	public Integer getAdjustedStock() {
		return adjustedStock;
	}

	public String getReason() {
		return reason;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setAdjustedStock(Integer adjustedStock) {
		this.adjustedStock = adjustedStock;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Boolean getAdd() {
		return add;
	}

	public Boolean getRemove() {
		return remove;
	}

	public void setAdd(Boolean add) {
		this.add = add;
	}

	public void setRemove(Boolean remove) {
		this.remove = remove;
	}

}
