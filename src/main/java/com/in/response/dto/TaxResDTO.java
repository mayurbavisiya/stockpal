package com.in.response.dto;

public class TaxResDTO {

	private Long id;
	private String rateName;
	private String amount;
	private Integer taxTypeId;
	private String taxTypeName;
	private Integer isDefault;

	public Long getId() {
		return id;
	}

	public String getRateName() {
		return rateName;
	}

	public String getAmount() {
		return amount;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRateName(String rateName) {
		this.rateName = rateName;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Integer getTaxTypeId() {
		return taxTypeId;
	}

	public String getTaxTypeName() {
		return taxTypeName;
	}

	public void setTaxTypeId(Integer taxTypeId) {
		this.taxTypeId = taxTypeId;
	}

	public void setTaxTypeName(String taxTypeName) {
		this.taxTypeName = taxTypeName;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

}
