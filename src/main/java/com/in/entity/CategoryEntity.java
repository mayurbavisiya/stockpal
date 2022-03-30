package com.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "category")
public class CategoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "margin_percentage")
	private Double marginPercentage;

	@Column(name = "ebt_enabled")
	private Integer ebtEnabled;

	@Column(name = "description")
	private String description;

	@Column(name = "parent_category_id")
	private Integer parentCategoryId;

	@Column(name = "parent_category_name")
	private String parentCategoryName;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "is_active")
	private Integer isActive;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getMarginPercentage() {
		return marginPercentage;
	}

	public Integer getEbtEnabled() {
		return ebtEnabled;
	}

	public String getDescription() {
		return description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMarginPercentage(Double marginPercentage) {
		this.marginPercentage = marginPercentage;
	}

	public void setEbtEnabled(Integer ebtEnabled) {
		this.ebtEnabled = ebtEnabled;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Integer parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public String getParentCategoryName() {
		return parentCategoryName == null ? "" : parentCategoryName;
	}

	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

}
