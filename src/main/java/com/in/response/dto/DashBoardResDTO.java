package com.in.response.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class DashBoardResDTO {
	@JsonInclude(Include.NON_NULL)
	private Double sales;

	@JsonInclude(Include.NON_NULL)
	private Double netProfit;

	@JsonInclude(Include.NON_NULL)
	private Double transaction;

	@JsonInclude(Include.NON_NULL)
	private Integer activeInventory;

	@JsonInclude(Include.NON_NULL)
	private Integer lowInventory;

	@JsonInclude(Include.NON_NULL)
	private Integer outOfStock;

	@JsonInclude(Include.NON_NULL)
	List<DashBoardPriceChangeResDTO> priceChangesData;

	@JsonInclude(Include.NON_NULL)
	List<DashBoardNewProductResDTO> newProductData;

	@JsonInclude(Include.NON_NULL)
	List<DashBoardBestSellerResDTO> bestSeller;

	@JsonInclude(Include.NON_NULL)
	List<DashBoardTopCategoryResDTO> topCategory;

	public Double getSales() {
		return sales;
	}

	public Double getNetProfit() {
		return netProfit;
	}

	public Double getTransaction() {
		return transaction;
	}

	public Integer getActiveInventory() {
		return activeInventory;
	}

	public Integer getLowInventory() {
		return lowInventory;
	}

	public Integer getOutOfStock() {
		return outOfStock;
	}

	public List<DashBoardPriceChangeResDTO> getPriceChangesData() {
		return priceChangesData;
	}

	public List<DashBoardNewProductResDTO> getNewProductData() {
		return newProductData;
	}

	public List<DashBoardBestSellerResDTO> getBestSeller() {
		return bestSeller;
	}

	public List<DashBoardTopCategoryResDTO> getTopCategory() {
		return topCategory;
	}

	public void setSales(Double sales) {
		this.sales = sales;
	}

	public void setNetProfit(Double netProfit) {
		this.netProfit = netProfit;
	}

	public void setTransaction(Double transaction) {
		this.transaction = transaction;
	}

	public void setActiveInventory(Integer activeInventory) {
		this.activeInventory = activeInventory;
	}

	public void setLowInventory(Integer lowInventory) {
		this.lowInventory = lowInventory;
	}

	public void setOutOfStock(Integer outOfStock) {
		this.outOfStock = outOfStock;
	}

	public void setPriceChangesData(List<DashBoardPriceChangeResDTO> priceChangesData) {
		this.priceChangesData = priceChangesData;
	}

	public void setNewProductData(List<DashBoardNewProductResDTO> newProductData) {
		this.newProductData = newProductData;
	}

	public void setBestSeller(List<DashBoardBestSellerResDTO> bestSeller) {
		this.bestSeller = bestSeller;
	}

	public void setTopCategory(List<DashBoardTopCategoryResDTO> topCategory) {
		this.topCategory = topCategory;
	}

}
