package com.in.response.dto;

public class ProductStockAdjustmentResDTO {

	private Long id;
	private Integer productId;
	private String adjustedDate;
	private Integer adjustedStock;
	private String reason;

	public Long getId() {
		return id;
	}

	public Integer getProductId() {
		return productId;
	}

	public String getAdjustedDate() {
		return adjustedDate;
	}

	public String getReason() {
		return reason;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setAdjustedDate(String adjustedDate) {
		this.adjustedDate = adjustedDate;
	}

	public Integer getAdjustedStock() {
		return adjustedStock;
	}

	public void setAdjustedStock(Integer adjustedStock) {
		this.adjustedStock = adjustedStock;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
