package com.in.response.dto;

import java.util.List;

public class ProductOtherDetailsResDTO {

	private Integer qtyDetails;
	private Integer promotionId;
	private String expiryDate;
	private Integer alertBeforeDays;
	private Integer ebtEnabled;

	List<ProductPackResDTO> productPacks;

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

	public List<ProductPackResDTO> getProductPacks() {
		return productPacks;
	}

	public void setQtyDetails(Integer qtyDetails) {
		this.qtyDetails = qtyDetails == null ? 0 : qtyDetails;
	}

	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId == null ? 0 : promotionId;
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

	public void setProductPacks(List<ProductPackResDTO> productPacks) {
		this.productPacks = productPacks;
	}

}
