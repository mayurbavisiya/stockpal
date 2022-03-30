package com.in.request.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductReqDTO {

	private Boolean showPOS;
	private String name;
	private String productImageURL;
	private Integer categoryId;
	private String barCode;
	private String sku;
	private String productDesc;
	private String tags;
	private Integer sizeId;
	private Integer volume;

	@JsonProperty("productPricing")
	ProductPricingReqDTO productPricing;

	@JsonProperty("productStockDetails")
	ProductStockReqDTO productStockDetails;

	@JsonProperty("productOtherDetails")
	ProductOtherDetailsReqDTO productOtherDetails;

	public Boolean getShowPOS() {
		return showPOS;
	}

	public String getName() {
		return name;
	}

	public String getProductImageURL() {
		return productImageURL;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public String getBarCode() {
		return barCode;
	}

	public String getSku() {
		return sku;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public String getTags() {
		return tags;
	}

	public Integer getSizeId() {
		return sizeId;
	}

	public ProductPricingReqDTO getProductPricing() {
		return productPricing;
	}

	public ProductStockReqDTO getProductStockDetails() {
		return productStockDetails;
	}

	public ProductOtherDetailsReqDTO getProductOtherDetails() {
		return productOtherDetails;
	}

	public void setShowPOS(Boolean showPOS) {
		this.showPOS = showPOS;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProductImageURL(String productImageURL) {
		this.productImageURL = productImageURL;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}

	public void setProductPricing(ProductPricingReqDTO productPricing) {
		this.productPricing = productPricing;
	}

	public void setProductStockDetails(ProductStockReqDTO productStockDetails) {
		this.productStockDetails = productStockDetails;
	}

	public void setProductOtherDetails(ProductOtherDetailsReqDTO productOtherDetails) {
		this.productOtherDetails = productOtherDetails;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

}
