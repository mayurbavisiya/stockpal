package com.in.request.dto;

public class POSOrderItemsReqDTO {

	private Long productId;
	private Integer sizeId;
	private Integer unit;
	private Integer qty;
	private Integer isPack;
	private Integer packId;

	public Long getProductId() {
		return productId;
	}

	public Integer getQty() {
		return qty;
	}

	public Integer getIsPack() {
		return isPack;
	}

	public Integer getPackId() {
		return packId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public void setIsPack(Integer isPack) {
		this.isPack = isPack;
	}

	public void setPackId(Integer packId) {
		this.packId = packId;
	}

	public Integer getSizeId() {
		return sizeId;
	}

	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

}
