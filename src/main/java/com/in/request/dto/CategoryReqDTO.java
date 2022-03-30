package com.in.request.dto;

public class CategoryReqDTO {
	private String name;
	private Long parentCategoryId;
	private String marginPercentage;
	private Boolean ebtEnabled;
	private String description;
	private String productIds;
	private Integer isAllSelected;

	public String getName() {
		return name;
	}

	public String getMarginPercentage() {
		return marginPercentage;
	}

	public Boolean getEbtEnabled() {
		return ebtEnabled;
	}

	public String getDescription() {
		return description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMarginPercentage(String marginPercentage) {
		this.marginPercentage = marginPercentage;
	}

	public void setEbtEnabled(Boolean ebtEnabled) {
		this.ebtEnabled = ebtEnabled;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public Integer getIsAllSelected() {
		return isAllSelected;
	}

	public void setIsAllSelected(Integer isAllSelected) {
		this.isAllSelected = isAllSelected;
	}

	public Long getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Long parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

}
