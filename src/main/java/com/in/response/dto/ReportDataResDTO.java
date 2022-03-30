package com.in.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ReportDataResDTO {

	@JsonInclude(Include.NON_NULL)
	private Integer productId;

	@JsonInclude(Include.NON_NULL)
	private String productName;

	@JsonInclude(Include.NON_NULL)
	private String stockForeCast;

	@JsonInclude(Include.NON_NULL)
	private String SKU;

	@JsonInclude(Include.NON_NULL)
	private String categoryName;

	@JsonInclude(Include.NON_NULL)
	private String vendorName;

	@JsonInclude(Include.NON_NULL)
	private String lastSaleDate;

	@JsonInclude(Include.NON_NULL)
	private Integer parLevel;

	@JsonInclude(Include.NON_NULL)
	private Integer reOrderQty;

	@JsonInclude(Include.NON_NULL)
	private Integer qtySold;

	@JsonInclude(Include.NON_NULL)
	private Double totalPrice;

	@JsonInclude(Include.NON_NULL)
	private Double costOfGoodSold;

	@JsonInclude(Include.NON_NULL)
	private Double totalProfit;

	@JsonInclude(Include.NON_NULL)
	private Integer inStockQty;

	@JsonInclude(Include.NON_NULL)
	private Double costPerUnit;

	@JsonInclude(Include.NON_NULL)
	private Double totalValue;

	@JsonInclude(Include.NON_NULL)
	private Double estimatedRevenue;

	@JsonInclude(Include.NON_NULL)
	private Double estimatedProfit;

	@JsonInclude(Include.NON_NULL)
	private Double discount;

	public Integer getProductId() {
		return productId == null ? 0 : productId;
	}

	public String getProductName() {
		return productName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getVendorName() {
		return vendorName;
	}

	public String getLastSaleDate() {
		return lastSaleDate;
	}

	public Integer getParLevel() {
		return parLevel == null ? 0 : parLevel;
	}

	public Integer getReOrderQty() {
		return reOrderQty == null ? 0 : reOrderQty;
	}

	public Integer getQtySold() {
		return qtySold == null ? 0 : qtySold;
	}

	public Double getTotalPrice() {
		return totalPrice == null ? 0.0 : totalPrice;
	}

	public Double getCostOfGoodSold() {
		return costOfGoodSold == null ? 0.0 : costOfGoodSold;
	}

	public Double getTotalProfit() {
		return totalProfit == null ? 0.0 : totalProfit;
	}

	public Integer getInStockQty() {
		return inStockQty == null ? 0 : inStockQty;
	}

	public Double getCostPerUnit() {
		return costPerUnit == null ? 0.0 : costPerUnit;
	}

	public Double getTotalValue() {
		return totalValue == null ? 0.0 : totalValue;
	}

	public Double getEstimatedRevenue() {
		return estimatedRevenue == null ? 0.0 : estimatedRevenue;
	}

	public Double getEstimatedProfit() {
		return estimatedProfit == null ? 0.0 : estimatedProfit;
	}

	public Double getDiscount() {
		return discount == null ? 0.0 : discount;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public void setLastSaleDate(String lastSaleDate) {
		this.lastSaleDate = lastSaleDate;
	}

	public void setParLevel(Integer parLevel) {
		this.parLevel = parLevel;
	}

	public void setReOrderQty(Integer reOrderQty) {
		this.reOrderQty = reOrderQty;
	}

	public void setQtySold(Integer qtySold) {
		this.qtySold = qtySold;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setCostOfGoodSold(Double costOfGoodSold) {
		this.costOfGoodSold = costOfGoodSold;
	}

	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}

	public void setInStockQty(Integer inStockQty) {
		this.inStockQty = inStockQty;
	}

	public void setCostPerUnit(Double costPerUnit) {
		this.costPerUnit = costPerUnit;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public void setEstimatedRevenue(Double estimatedRevenue) {
		this.estimatedRevenue = estimatedRevenue;
	}

	public void setEstimatedProfit(Double estimatedProfit) {
		this.estimatedProfit = estimatedProfit;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getSKU() {
		return SKU;
	}

	public void setSKU(String sKU) {
		SKU = sKU;
	}

	public String getStockForeCast() {
		return stockForeCast;
	}

	public void setStockForeCast(String stockForeCast) {
		this.stockForeCast = stockForeCast;
	}

}
