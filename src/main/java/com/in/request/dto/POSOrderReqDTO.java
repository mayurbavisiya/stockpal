package com.in.request.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class POSOrderReqDTO {

	private Integer custId;

	@JsonProperty("posOrderItems")
	List<POSOrderItemsReqDTO> posOrderItems;

	public Integer getCustId() {
		return custId;
	}

	public List<POSOrderItemsReqDTO> getPOSOrderItems() {
		return posOrderItems;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public void setPOSOrderItems(List<POSOrderItemsReqDTO> pOSOrderItems) {
		posOrderItems = pOSOrderItems;
	}

}
