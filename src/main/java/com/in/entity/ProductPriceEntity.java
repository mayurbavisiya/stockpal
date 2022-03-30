package com.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "product_price")
public class ProductPriceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "pp_id")
	private Long id;

	@Column(name = "cost_price")
	private Double costPrice;

	@Column(name = "margin")
	private Double margin;

	@Column(name = "price_type")
	private Integer priceType;

	@Column(name = "unit")
	private Integer unit;

	@Column(name = "selling_price")
	private Double sellingPrice;

	@Column(name = "tax")
	private Integer tax;

	@Column(name = "vendor_name")
	private Integer vendorName;

	@Column(name = "old_selling_price")
	private Double oldSellingPrice;

	@Column(name = "price_update_date")
	private Date priceUpdateDate;

	@ManyToOne
	private ProductEntity product;

	public Long getId() {
		return id;
	}

	public Double getMargin() {
		return margin;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public Integer getTax() {
		return tax;
	}

	public Integer getVendorName() {
		return vendorName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public void setTax(Integer tax) {
		this.tax = tax;
	}

	public void setVendorName(Integer vendorName) {
		this.vendorName = vendorName;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public Double getOldSellingPrice() {
		return oldSellingPrice;
	}

	public void setOldSellingPrice(Double oldSellingPrice) {
		this.oldSellingPrice = oldSellingPrice;
	}

	public Date getPriceUpdateDate() {
		return priceUpdateDate;
	}

	public void setPriceUpdateDate(Date priceUpdateDate) {
		this.priceUpdateDate = priceUpdateDate;
	}

}
