package com.in.request.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductOtherDetailsReqDTO {

	private Integer qtyDetails;
	private Integer promotionId;
	private String expiryDate;
	private Integer alertBeforeDays;
	private Integer ebtEnabled;

	@JsonProperty("productPacks")
	List<ProductPackReqDTO> productPacks;

	public Integer getQtyDetails() {
		return qtyDetails;
	}

	public Integer getPromotionId() {
		return promotionId;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public Integer getAlertBeforeDays() {
		return alertBeforeDays;
	}

	public Integer getEbtEnabled() {
		return ebtEnabled;
	}

	public List<ProductPackReqDTO> getProductPacks() {
		return productPacks;
	}

	public void setQtyDetails(Integer qtyDetails) {
		this.qtyDetails = qtyDetails;
	}

	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setAlertBeforeDays(Integer alertBeforeDays) {
		this.alertBeforeDays = alertBeforeDays;
	}

	public void setEbtEnabled(Integer ebtEnabled) {
		this.ebtEnabled = ebtEnabled;
	}

	public void setProductPacks(List<ProductPackReqDTO> productPacks) {
		this.productPacks = productPacks;
	}

}
