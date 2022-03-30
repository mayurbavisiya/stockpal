package com.in.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "promotion_type_days")
public class PromotionTypeDaysEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	@Column(name = "day_id")
	private Integer dayId;

	@Column(name = "promotion_time_period_id")
	private Integer promotionTimePeriodiId;

	@ManyToOne
	PromotionEntity promotion;

	public Long getId() {
		return id;
	}

	public Integer getDayId() {
		return dayId;
	}

	public PromotionEntity getPromotion() {
		return promotion;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDayId(Integer dayId) {
		this.dayId = dayId;
	}

	public void setPromotion(PromotionEntity promotion) {
		this.promotion = promotion;
	}

	public Integer getPromotionTimePeriodiId() {
		return promotionTimePeriodiId;
	}

	public void setPromotionTimePeriodiId(Integer promotionTimePeriodiId) {
		this.promotionTimePeriodiId = promotionTimePeriodiId;
	}

}
