package com.in.request.dto;

public class TaxRequestDTO {

	private String rateName;
	private String amount;
	private Integer taxType;
	private Integer isDefault;

	public String getRateName() {
		return rateName;
	}

	public String getAmount() {
		return amount;
	}

	public void setRateName(String rateName) {
		this.rateName = rateName;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Integer getTaxType() {
		return taxType;
	}

	public void setTaxType(Integer taxType) {
		this.taxType = taxType;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

}
