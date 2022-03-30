package com.in.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class DashBoardPriceChangeResDTO {
	@JsonInclude(Include.NON_NULL)
	private String name;

	@JsonInclude(Include.NON_NULL)
	private Integer productId;

	@JsonInclude(Include.NON_NULL)
	private Double oldPrice;

	@JsonInclude(Include.NON_NULL)
	private Double newPrice;

	public String getName() {
		return name;
	}

	public Double getOldPrice() {
		return oldPrice;
	}

	public Double getNewPrice() {
		return newPrice;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOldPrice(Double oldPrice) {
		this.oldPrice = oldPrice;
	}

	public void setNewPrice(Double newPrice) {
		this.newPrice = newPrice;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

}
