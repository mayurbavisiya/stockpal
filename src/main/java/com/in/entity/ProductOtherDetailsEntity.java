package com.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "product_other_details")
public class ProductOtherDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "p_o_d_id")
	private Long id;

	@Column(name = "quantity_details")
	private Integer quantityDetails;

	@Column(name = "promotions")
	private Integer promotions;

	@Column(name = "promotion_expiry_date")
	private Date promotionExpiryDate;

	@Column(name = "alert_before_days")
	private Integer alertBeforeDays;

	@Column(name = "EBT_enabled")
	private Integer ebtEnabled;

	@ManyToOne
	private ProductEntity product;

	public Long getId() {
		return id;
	}

	public Integer getQuantityDetails() {
		return quantityDetails;
	}

	public Integer getPromotions() {
		return promotions;
	}

	public Date getPromotionExpiryDate() {
		return promotionExpiryDate;
	}

	public Integer getAlertBeforeDays() {
		return alertBeforeDays;
	}

	public Integer getEbtEnabled() {
		return ebtEnabled;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setQuantityDetails(Integer quantityDetails) {
		this.quantityDetails = quantityDetails;
	}

	public void setPromotions(Integer promotions) {
		this.promotions = promotions;
	}

	public void setPromotionExpiryDate(Date promotionExpiryDate) {
		this.promotionExpiryDate = promotionExpiryDate;
	}

	public void setAlertBeforeDays(Integer alertBeforeDays) {
		this.alertBeforeDays = alertBeforeDays;
	}

	public void setEbtEnabled(Integer ebtEnabled) {
		this.ebtEnabled = ebtEnabled;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

}
