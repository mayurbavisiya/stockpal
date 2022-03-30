package com.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "product_stock_adjustment")
public class ProductStockAdjustementEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	@Column(name = "product_id")
	private Integer productId;

	@Column(name = "adjusted_date")
	private Date adjustedDate;

	@Column(name = "adjusted_stock")
	private Integer adjustedStock;

	@Column(name = "reason")
	private String reason;

	public Long getId() {
		return id;
	}

	public Integer getProductId() {
		return productId;
	}

	public Date getAdjustedDate() {
		return adjustedDate;
	}

	public String getReason() {
		return reason;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setAdjustedDate(Date adjustedDate) {
		this.adjustedDate = adjustedDate;
	}

	public Integer getAdjustedStock() {
		return adjustedStock;
	}

	public void setAdjustedStock(Integer adjustedStock) {
		this.adjustedStock = adjustedStock;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
