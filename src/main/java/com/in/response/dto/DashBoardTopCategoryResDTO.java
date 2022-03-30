package com.in.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class DashBoardTopCategoryResDTO {
	@JsonInclude(Include.NON_NULL)
	private String name;

	@JsonInclude(Include.NON_NULL)
	private Integer qtySold;

	@JsonInclude(Include.NON_NULL)
	private Integer categoryId;

	@JsonInclude(Include.NON_NULL)
	private Double totalSales;

	public String getName() {
		return name;
	}

	public Integer getQtySold() {
		return qtySold;
	}

	public Double getTotalSales() {
		return totalSales;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQtySold(Integer qtySold) {
		this.qtySold = qtySold;
	}

	public void setTotalSales(Double totalSales) {
		this.totalSales = totalSales;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

}
