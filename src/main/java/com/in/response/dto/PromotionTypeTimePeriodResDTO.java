package com.in.response.dto;

import java.util.List;

public class PromotionTypeTimePeriodResDTO {

	private Long id;
	private String from;
	private String to;
	private List<PromotionTypeDaysResDTO> promotionTypeDays;

	public Long getId() {
		return id;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public List<PromotionTypeDaysResDTO> getPromotionTypeDays() {
		return promotionTypeDays;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setPromotionTypeDays(List<PromotionTypeDaysResDTO> promotionTypeDays) {
		this.promotionTypeDays = promotionTypeDays;
	}

}
