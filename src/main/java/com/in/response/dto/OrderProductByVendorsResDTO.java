package com.in.response.dto;

public class OrderProductByVendorsResDTO {

	private Integer productId;
	private String productName;
	private String lastOrderDate;
	private Integer qtyOrdered;
	private Integer qtySold30Days;
	private Integer qtySold7Days;
	private Integer inStock;
	private String stockForeCast;
	private Integer reOrderQty;
	private Integer suggestedOrderQty;

	private Double costPrice;
	private Double margin;
	private Double sellingPrice;

	public Integer getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public String getLastOrderDate() {
		return lastOrderDate;
	}

	public Integer getQtyOrdered() {
		return qtyOrdered;
	}

	public Integer getQtySold30Days() {
		return qtySold30Days;
	}

	public Integer getQtySold7Days() {
		return qtySold7Days;
	}

	public Integer getInStock() {
		return inStock;
	}

	public Integer getReOrderQty() {
		return reOrderQty;
	}

	public Integer getSuggestedOrderQty() {
		return suggestedOrderQty;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setLastOrderDate(String lastOrderDate) {
		this.lastOrderDate = lastOrderDate;
	}

	public void setQtyOrdered(Integer qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public void setQtySold30Days(Integer qtySold30Days) {
		this.qtySold30Days = qtySold30Days;
	}

	public void setQtySold7Days(Integer qtySold7Days) {
		this.qtySold7Days = qtySold7Days;
	}

	public void setInStock(Integer inStock) {
		this.inStock = inStock;
	}

	public void setReOrderQty(Integer reOrderQty) {
		this.reOrderQty = reOrderQty;
	}

	public void setSuggestedOrderQty(Integer suggestedOrderQty) {
		this.suggestedOrderQty = suggestedOrderQty;
	}

	public String getStockForeCast() {
		return stockForeCast;
	}

	public void setStockForeCast(String stockForeCast) {
		this.stockForeCast = stockForeCast;
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

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

}