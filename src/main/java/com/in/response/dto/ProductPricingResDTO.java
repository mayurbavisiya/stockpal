package com.in.response.dto;

public class ProductPricingResDTO {

	private Double costPrice;
	private Double margin;
	private Integer priceTypeId;
	private Integer unit;
	private Double sellingPrice;
	private Integer taxId;
	private Integer vendorNameId;
	private String vendorName;

	public Double getCostPrice() {
		return costPrice;
	}

	public Double getMargin() {
		return margin;
	}

	public Integer getPriceTypeId() {
		return priceTypeId;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public Integer getTaxId() {
		return taxId;
	}

	public Integer getVendorNameId() {
		return vendorNameId;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public void setPriceTypeId(Integer priceTypeId) {
		this.priceTypeId = priceTypeId;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public void setTaxId(Integer taxId) {
		this.taxId = taxId;
	}

	public void setVendorNameId(Integer vendorNameId) {
		this.vendorNameId = vendorNameId;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

}
