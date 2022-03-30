package com.in.response.dto;

public class ProductPackResDTO {

	private Long id;
	private String packName;
	private Integer totalPacksPerCase;
	private Integer singlePerCase;
	private Double caseCost;
	private Double casePrice;
	private Integer caseParLevel;
	private Integer caseReorderQty;
	private String sku;
	private String barCode;

	public String getPackName() {
		return packName;
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

	public Integer getCaseReorderQty() {
		return caseReorderQty;
	}

	public String getSku() {
		return sku;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setPackName(String packName) {
		this.packName = packName;
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

	public void setCaseReorderQty(Integer caseReorderQty) {
		this.caseReorderQty = caseReorderQty;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
