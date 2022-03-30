package com.in.response.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ReportResDTO {

	@JsonInclude(Include.NON_NULL)
	private Double totalSales;

	@JsonInclude(Include.NON_NULL)
	private Double productSold;

	@JsonInclude(Include.NON_NULL)
	private Double netProfit;

	@JsonInclude(Include.NON_NULL)
	List<ReportDataResDTO> data;

	@JsonInclude(Include.NON_NULL)
	private Integer inventoryStocks;

	@JsonInclude(Include.NON_NULL)
	private Double inventoryValue;

	@JsonInclude(Include.NON_NULL)
	private Double estimatedProfit;

	public Double getTotalSales() {
		return totalSales;
	}

	public Double getProductSold() {
		return productSold;
	}

	public Double getNetProfit() {
		return netProfit;
	}

	public void setTotalSales(Double totalSales) {
		this.totalSales = totalSales;
	}

	public void setProductSold(Double productSold) {
		this.productSold = productSold;
	}

	public void setNetProfit(Double netProfit) {
		this.netProfit = netProfit;
	}

	public List<ReportDataResDTO> getData() {
		return data;
	}

	public void setData(List<ReportDataResDTO> data) {
		this.data = data;
	}

	public Integer getInventoryStocks() {
		return inventoryStocks;
	}

	public Double getInventoryValue() {
		return inventoryValue;
	}

	public Double getEstimatedProfit() {
		return estimatedProfit;
	}

	public void setInventoryStocks(Integer inventoryStocks) {
		this.inventoryStocks = inventoryStocks;
	}

	public void setInventoryValue(Double inventoryValue) {
		this.inventoryValue = inventoryValue;
	}

	public void setEstimatedProfit(Double estimatedProfit) {
		this.estimatedProfit = estimatedProfit;
	}

}
