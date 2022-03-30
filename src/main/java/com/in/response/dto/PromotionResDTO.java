package com.in.response.dto;

import java.util.List;

public class PromotionResDTO {

	private Long id;
	private String name;
	private Integer statusId;
	private Integer durationId;
	private String startDate;
	private String endDate;
	private String promotionDesc;

	private PromotionTypeResDTO promotionType;
	private List<PromotionTypeProductMapResDTO> prmotionMappedProducts;
	private List<PromotionTypeTimePeriodResDTO> promotionTimePeriod;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public Integer getDurationId() {
		return durationId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public void setDurationId(Integer durationId) {
		this.durationId = durationId;
	}

	public String getPromotionDesc() {
		return promotionDesc;
	}

	public void setPromotionDesc(String promotionDesc) {
		this.promotionDesc = promotionDesc;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public PromotionTypeResDTO getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(PromotionTypeResDTO promotionType) {
		this.promotionType = promotionType;
	}

	public List<PromotionTypeProductMapResDTO> getPrmotionMappedProducts() {
		return prmotionMappedProducts;
	}

	public List<PromotionTypeTimePeriodResDTO> getPromotionTimePeriod() {
		return promotionTimePeriod;
	}

	public void setPrmotionMappedProducts(List<PromotionTypeProductMapResDTO> prmotionMappedProducts) {
		this.prmotionMappedProducts = prmotionMappedProducts;
	}

	public void setPromotionTimePeriod(List<PromotionTypeTimePeriodResDTO> promotionTimePeriod) {
		this.promotionTimePeriod = promotionTimePeriod;
	}

}
