package com.in.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "product_pack")
public class ProductPackEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "p_p_id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "total_packs_per_case")
	private Integer totalPacksPerCase;

	@Column(name = "single_per_case")
	private Integer singlePerCase;

	@Column(name = "case_cost")
	private Double caseCost;

	@Column(name = "case_price")
	private Double casePrice;

	@Column(name = "case_par_level")
	private Integer caseParLevel;

	@Column(name = "case_reorder_qty")
	private Integer caseReOrderQty;

	@Column(name = "barcode")
	private String barCode;

	@Column(name = "SKU")
	private String sku;

	@ManyToOne
	private ProductEntity product;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getTotalPacksPerCase() {
		return totalPacksPerCase;
	}

	public Integer getSinglePerCase() {
		return singlePerCase;
	}

	public Double getCaseCost() {
		return caseCost;
	}

	public Double getCasePrice() {
		return casePrice;
	}

	public Integer getCaseParLevel() {
		return caseParLevel;
	}

	public Integer getCaseReOrderQty() {
		return caseReOrderQty;
	}

	public String getBarCode() {
		return barCode;
	}

	public String getSku() {
		return sku;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTotalPacksPerCase(Integer totalPacksPerCase) {
		this.totalPacksPerCase = totalPacksPerCase;
	}

	public void setSinglePerCase(Integer singlePerCase) {
		this.singlePerCase = singlePerCase;
	}

	public void setCaseCost(Double caseCost) {
		this.caseCost = caseCost;
	}

	public void setCasePrice(Double casePrice) {
		this.casePrice = casePrice;
	}

	public void setCaseParLevel(Integer caseParLevel) {
		this.caseParLevel = caseParLevel;
	}

	public void setCaseReOrderQty(Integer caseReOrderQty) {
		this.caseReOrderQty = caseReOrderQty;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

}
