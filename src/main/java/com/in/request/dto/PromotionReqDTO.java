package com.in.request.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PromotionReqDTO {
	private String name;
	private Integer statusId;
	private Integer durationId;
	private String startDate;
	private String endDate;
	private String promotionDesc;

	@JsonProperty("promotionType")
	PromotionTypeReqDTO promotionType;

	@JsonProperty("timePeriod")
	List<PromotionTimePeriodReqDTO> promotionTimePeriods;

	@JsonProperty("products")
	PromotionProductReqDTO promotionProduct;

	public String getName() {
		return name;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public Integer getDurationId() {
		return durationId;
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

	public String getPromotionDesc() {
		return promotionDesc;
	}

	public void setPromotionDesc(String promotionDesc) {
		this.promotionDesc = promotionDesc;
	}

	public PromotionTypeReqDTO getPromotionType() {
		return promotionType;
	}

	public List<PromotionTimePeriodReqDTO> getPromotionTimePeriods() {
		return promotionTimePeriods;
	}

	public PromotionProductReqDTO getPromotionProduct() {
		return promotionProduct;
	}

	public void setPromotionType(PromotionTypeReqDTO promotionType) {
		this.promotionType = promotionType;
	}

	public void setPromotionTimePeriods(List<PromotionTimePeriodReqDTO> promotionTimePeriods) {
		this.promotionTimePeriods = promotionTimePeriods;
	}

	public void setPromotionProduct(PromotionProductReqDTO promotionProduct) {
		this.promotionProduct = promotionProduct;
	}

}
