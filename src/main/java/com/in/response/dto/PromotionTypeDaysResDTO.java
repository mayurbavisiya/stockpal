package com.in.response.dto;

public class PromotionTypeDaysResDTO {

	private Long id;
	private Integer dayId;
	private Integer promotionTimePeriodiId;

	public Long getId() {
		return id;
	}

	public Integer getDayId() {
		return dayId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDayId(Integer dayId) {
		this.dayId = dayId;
	}

	public Integer getPromotionTimePeriodiId() {
		return promotionTimePeriodiId;
	}

	public void setPromotionTimePeriodiId(Integer promotionTimePeriodiId) {
		this.promotionTimePeriodiId = promotionTimePeriodiId;
	}

}
