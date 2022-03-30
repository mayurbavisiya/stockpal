package com.in.response.dto;

public class ProductStockResDTO {

	private Integer stockQty;
	private Integer reorderQty;
	private Integer parLevel;

	public Integer getStockQty() {
		return stockQty;
	}

	public Integer getReorderQty() {
		return reorderQty;
	}

	public Integer getParLevel() {
		return parLevel;
	}

	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}

	public void setReorderQty(Integer reorderQty) {
		this.reorderQty = reorderQty;
	}

	public void setParLevel(Integer parLevel) {
		this.parLevel = parLevel;
	}

}
