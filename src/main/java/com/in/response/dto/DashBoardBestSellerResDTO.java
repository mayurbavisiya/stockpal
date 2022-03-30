package com.in.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class DashBoardBestSellerResDTO {
	@JsonInclude(Include.NON_NULL)
	private String name;

	@JsonInclude(Include.NON_NULL)
	private Integer qtySold;

	@JsonInclude(Include.NON_NULL)
	private Integer productId;

	@JsonInclude(Include.NON_NULL)
	private Double sales;

	public String getName() {
		return name;
	}

	public Integer getQtySold() {
		return qtySold;
	}

	public Double getSales() {
		return sales;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQtySold(Integer qtySold) {
		this.qtySold = qtySold;
	}

	public void setSales(Double sales) {
		this.sales = sales;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

}
