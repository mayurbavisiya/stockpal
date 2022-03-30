package com.in.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "promotion_type_time_period")
public class PromotionTypeTimePeriodEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	@Column(name = "from_time")
	private String from;

	@Column(name = "to_time")
	private String to;

	@ManyToOne
	PromotionEntity promotion;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "promotion_time_period_id")
	private Set<PromotionTypeDaysEntity> promotionTypeDays;

	public Long getId() {
		return id;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public PromotionEntity getPromotion() {
		return promotion;
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

	public void setPromotion(PromotionEntity promotion) {
		this.promotion = promotion;
	}

	public Set<PromotionTypeDaysEntity> getPromotionTypeDays() {
		return promotionTypeDays;
	}

	public void setPromotionTypeDays(Set<PromotionTypeDaysEntity> promotionTypeDays) {
		this.promotionTypeDays = promotionTypeDays;
	}

}
