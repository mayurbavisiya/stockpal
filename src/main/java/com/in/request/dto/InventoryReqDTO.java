package com.in.request.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InventoryReqDTO {

	private Long productId;
	private Integer singleQty;

	@JsonProperty("productPacks")
	private List<InventoryPackReqDTO> packs;

	public Long getProductId() {
		return productId;
	}

	public Integer getSingleQty() {
		return singleQty;
	}

	public List<InventoryPackReqDTO> getPacks() {
		return packs;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setSingleQty(Integer singleQty) {
		this.singleQty = singleQty;
	}

	public void setPacks(List<InventoryPackReqDTO> packs) {
		this.packs = packs;
	}

}
