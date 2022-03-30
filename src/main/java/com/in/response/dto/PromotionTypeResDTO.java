package com.in.response.dto;

public class PromotionTypeResDTO {

	private Long id;
	private Integer applyId;
	private Integer buy;
	private Integer productId;
	private Integer getId;
	private Double getAmount;
	private Integer of;
	private Integer limitTo;
	private Integer discountOnEvery;
	private Integer buyingUnit;
	private Double discountAmount;


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

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	
}
