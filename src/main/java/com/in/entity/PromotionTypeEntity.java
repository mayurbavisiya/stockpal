package com.in.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "promotion_type")
public class PromotionTypeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	@Column(name = "apply_id")
	private Integer applyId;

	@Column(name = "buy")
	private Integer buy;

	@Column(name = "product_id")
	private Integer productId;

	@Column(name = "get_id")
	private Integer getId;

	@Column(name = "get_amount")
	private Double getAmount;

	@Column(name = "product_of")
	private Integer of;

	@Column(name = "limit_to")
	private Integer limitTo;

	@Column(name = "discount_on_every")
	private Integer discountOnEvery;

	@Column(name = "buying_unit")
	private Integer buyingUnit;

	@Column(name = "discount_amount")
	private Double discountAmount;

	@ManyToOne
	PromotionEntity promotion;

	public Long getId() {
		return id;
	}

	public Integer getApplyId() {
		return applyId;
	}

	public Integer getBuy() {
		return buy;
	}

	public Integer getProductId() {
		return productId;
	}

	public Integer getGetId() {
		return getId;
	}

	public Double getGetAmount() {
		return getAmount;
	}

	public Integer getOf() {
		return of;
	}

	public Integer getLimitTo() {
		return limitTo;
	}

	public Integer getDiscountOnEvery() {
		return discountOnEvery;
	}

	public Integer getBuyingUnit() {
		return buyingUnit;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}

	public void setBuy(Integer buy) {
		this.buy = buy;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setGetId(Integer getId) {
		this.getId = getId;
	}

	public void setGetAmount(Double getAmount) {
		this.getAmount = getAmount;
	}

	public void setOf(Integer of) {
		this.of = of;
	}

	public void setLimitTo(Integer limitTo) {
		this.limitTo = limitTo;
	}

	public void setDiscountOnEvery(Integer discountOnEvery) {
		this.discountOnEvery = discountOnEvery;
	}

	public void setBuyingUnit(Integer buyingUnit) {
		this.buyingUnit = buyingUnit;
	}

	public PromotionEntity getPromotion() {
		return promotion;
	}

	public void setPromotion(PromotionEntity promotion) {
		this.promotion = promotion;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

}
