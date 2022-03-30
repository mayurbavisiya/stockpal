package com.in.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class DashBoardNewProductResDTO {
	@JsonInclude(Include.NON_NULL)
	private String name;

	@JsonInclude(Include.NON_NULL)
	private Integer productId;

	@JsonInclude(Include.NON_NULL)
	private Integer inStock;

	@JsonInclude(Include.NON_NULL)
	private Double price;

	public String getName() {
		return name;
	}

	public Integer getInStock() {
		return inStock;
	}

	public Double getPrice() {
		return price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInStock(Integer inStock) {
		this.inStock = inStock;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

}
