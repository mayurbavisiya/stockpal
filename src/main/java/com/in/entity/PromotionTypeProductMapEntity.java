package com.in.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "promotion_type_product_map")
public class PromotionTypeProductMapEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	@Column(name = "product_id")
	private Integer productId;

	@ManyToOne
	PromotionEntity promotion;

	public Long getId() {
		return id;
	}

	public Integer getProductId() {
		return productId;
	}

	public PromotionEntity getPromotion() {
		return promotion;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setPromotion(PromotionEntity promotion) {
		this.promotion = promotion;
	}

}
