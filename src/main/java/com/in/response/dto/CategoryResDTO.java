package com.in.response.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class CategoryResDTO {
	private Long id;
	private String name;
	private String marginPercentage;
	private Boolean ebtEnabled;
	private String description;
	private Integer parentCategoryId;
	private String parentCategoryName;
	private String createdDate;
	private String updatedDate;

	@JsonInclude(Include.NON_NULL)
	private List<CategoryProductResDTO> products;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Integer getParentCategoryId() {
		return parentCategoryId;
	}

	public String getParentCategoryName() {
		return parentCategoryName;
	}

	public void setParentCategoryId(Integer parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<CategoryProductResDTO> getProducts() {
		return products;
	}

	public void setProducts(List<CategoryProductResDTO> products) {
		this.products = products;
	}

}
