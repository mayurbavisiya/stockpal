package com.in.response.dto;

import java.util.List;

public class ProductResDTO {

	private Long id;
	private Integer posFlag;
	private String name;
	private String imageURL;
	private Integer category;
	private String categoryName;
	private String barCode;
	private String SKU;
	private String desc;
	private Integer size;
	private Integer volume;
	private String createdDate;

	private ProductPricingResDTO productPriceDetails;
	private ProductStockResDTO productStockDetails;
	private ProductOtherDetailsResDTO productOtherDetails;
	private List<ProductTagsResDTO> productTags;

	public Long getId() {
		return id;
	}

	public Integer getPosFlag() {
		return posFlag;
	}

	public String getName() {
		return name;
	}

	public String getImageURL() {
		return imageURL;
	}

	public Integer getCategory() {
		return category;
	}

	public String getBarCode() {
		return barCode;
	}

	public String getSKU() {
		return SKU;
	}

	public String getDesc() {
		return desc;
	}

	public Integer getSize() {
		return size;
	}

	public ProductPricingResDTO getProductPriceDetails() {
		return productPriceDetails;
	}

	public ProductStockResDTO getProductStockDetails() {
		return productStockDetails;
	}

	public ProductOtherDetailsResDTO getProductOtherDetails() {
		return productOtherDetails;
	}

	public List<ProductTagsResDTO> getProductTags() {
		return productTags;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPosFlag(Integer posFlag) {
		this.posFlag = posFlag;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public void setSKU(String sKU) {
		SKU = sKU;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setProductPriceDetails(ProductPricingResDTO productPriceDetails) {
		this.productPriceDetails = productPriceDetails;
	}

	public void setProductStockDetails(ProductStockResDTO productStockDetails) {
		this.productStockDetails = productStockDetails;
	}

	public void setProductOtherDetails(ProductOtherDetailsResDTO productOtherDetails) {
		this.productOtherDetails = productOtherDetails;
	}

	public void setProductTags(List<ProductTagsResDTO> productTags) {
		this.productTags = productTags;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

}
