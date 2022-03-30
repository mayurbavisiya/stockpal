package com.in.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.repository.ReportRepository;
import com.in.response.dto.ReportDataResDTO;
import com.in.response.dto.ReportResDTO;
import com.in.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	ReportRepository reportRepository;

	@Override
	public APIResponse getSalesReport(Pageable pageable, String fromDate, String toDate, Integer status,
			Integer categoryId, Integer vendorId) {

		fromDate = (fromDate != null && !fromDate.isEmpty()) ? fromDate + " 00:00:00" : null;
		toDate = (toDate != null && !toDate.isEmpty()) ? toDate + " 23:59:59" : null;

		List<Object[]> headerData = reportRepository.getSalesReportHeaderValues(fromDate, toDate, status, categoryId,
				vendorId);
		Page<Object[]> salesPages = reportRepository.getSalesReportData(pageable, fromDate, toDate, status, categoryId,
				vendorId);

		List<Object[]> salesData = salesPages.getContent();
		Integer totalPages = salesPages.getTotalPages();
		Integer pageRecords = salesPages.getNumberOfElements();
		Integer totalRecords = (int) salesPages.getTotalElements();

		ReportResDTO saleResponse = new ReportResDTO();
		if (!headerData.isEmpty()) {
			Double totalSales = (headerData.get(0)[0] != null) ? Double.valueOf(String.valueOf(headerData.get(0)[0]))
					: 0.0;
			Double productSold = (headerData.get(0)[1] != null) ? Double.valueOf(String.valueOf(headerData.get(0)[1]))
					: 0.0;
			Double netProfit = (headerData.get(0)[2] != null) ? Double.valueOf(String.valueOf(headerData.get(0)[2]))
					: 0.0;
			saleResponse.setNetProfit(netProfit);
			saleResponse.setProductSold(productSold);
			saleResponse.setTotalSales(totalSales);
		}
		List<ReportDataResDTO> salesDataList = new ArrayList<ReportDataResDTO>();
		if (!salesData.isEmpty()) {
			for (Object[] obj : salesData) {
				ReportDataResDTO dto = new ReportDataResDTO();
				dto.setProductId(obj[0] != null ? Integer.parseInt(String.valueOf(obj[0])) : 0);
				dto.setProductName(obj[1] != null ? String.valueOf(obj[1]) : "");
				dto.setCategoryName(obj[2] != null ? String.valueOf(obj[2]) : "");
				dto.setVendorName(obj[3] != null ? String.valueOf(obj[3]) : "");
				dto.setQtySold(obj[4] != null ? Integer.valueOf(String.valueOf(obj[4])) : 0);
				dto.setTotalPrice(obj[5] != null ? Double.valueOf(String.valueOf(obj[5])) : 0);
				dto.setCostOfGoodSold(obj[6] != null ? Double.valueOf(String.valueOf(obj[6])) : 0);
				dto.setTotalProfit(obj[7] != null ? Double.valueOf(String.valueOf(obj[7])) : 0);
				salesDataList.add(dto);
			}
		}
		saleResponse.setData(salesDataList);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setReport(saleResponse);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;
	}

	@Override
	public APIResponse getDiscountReport(Pageable pageable, String fromDate, String toDate) {
		fromDate = (fromDate != null && !fromDate.isEmpty()) ? fromDate + " 00:00:00" : null;
		toDate = (toDate != null && !toDate.isEmpty()) ? toDate + " 23:59:59" : null;

		Page<Object[]> discountPage = reportRepository.getDiscountReportData(pageable, fromDate, toDate);
		Integer totalPages = discountPage.getTotalPages();
		Integer pageRecords = discountPage.getNumberOfElements();
		Integer totalRecords = (int) discountPage.getTotalElements();

		List<Object[]> discountData = discountPage.getContent();
		ReportResDTO discountResponse = new ReportResDTO();

		List<ReportDataResDTO> salesDataList = new ArrayList<ReportDataResDTO>();
		if (!discountData.isEmpty()) {
			for (Object[] obj : discountData) {
				ReportDataResDTO dto = new ReportDataResDTO();
				dto.setProductId(obj[0] != null ? Integer.parseInt(String.valueOf(obj[0])) : 0);
				dto.setProductName(obj[1] != null ? String.valueOf(obj[1]) : "");
				dto.setCategoryName(obj[2] != null ? String.valueOf(obj[2]) : "");
				dto.setVendorName(obj[3] != null ? String.valueOf(obj[3]) : "");
				dto.setQtySold(obj[4] != null ? Integer.valueOf(String.valueOf(obj[4])) : 0);
				dto.setTotalPrice(obj[5] != null ? Double.valueOf(String.valueOf(obj[5])) : 0);
				dto.setCostOfGoodSold(obj[6] != null ? Double.valueOf(String.valueOf(obj[6])) : 0);
				dto.setDiscount(obj[7] != null ? Double.valueOf(String.valueOf(obj[7])) : 0);
				dto.setTotalProfit(obj[8] != null ? Double.valueOf(String.valueOf(obj[8])) : 0);
				salesDataList.add(dto);
			}
		}
		discountResponse.setData(salesDataList);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setReport(discountResponse);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;

	}

	@Override
	public APIResponse getVendorsSalesReport(Pageable pageable, String fromDate, String toDate, Integer status,
			Integer categoryId, Integer vendorId) {

		List<Object[]> headerData = reportRepository.getVendorSalesReportHeaderValues(fromDate, toDate, status,
				categoryId, vendorId);
		Page<Object[]> vendorSalesPages = reportRepository.getVendorSalesReportData(pageable, fromDate, toDate, status,
				categoryId, vendorId);

		List<Object[]> vendorSalesData = vendorSalesPages.getContent();
		Integer totalPages = vendorSalesPages.getTotalPages();
		Integer pageRecords = vendorSalesPages.getNumberOfElements();
		Integer totalRecords = (int) vendorSalesPages.getTotalElements();

		ReportResDTO saleResponse = new ReportResDTO();
		if (!headerData.isEmpty()) {
			Integer inventotyStock = (headerData.get(0)[0] != null)
					? Integer.valueOf(String.valueOf(headerData.get(0)[0])) : 0;
			Double inventoryValue = (headerData.get(0)[1] != null)
					? Double.valueOf(String.valueOf(headerData.get(0)[1])) : 0.0;
			Double estimatedProfit = (headerData.get(0)[2] != null)
					? Double.valueOf(String.valueOf(headerData.get(0)[2])) : 0.0;
			saleResponse.setInventoryStocks(inventotyStock);
			saleResponse.setInventoryValue(inventoryValue);
			saleResponse.setEstimatedProfit(estimatedProfit);
		}
		List<ReportDataResDTO> salesDataList = new ArrayList<ReportDataResDTO>();
		if (!vendorSalesData.isEmpty()) {
			for (Object[] obj : vendorSalesData) {
				ReportDataResDTO dto = new ReportDataResDTO();
				dto.setProductId(obj[0] != null ? Integer.parseInt(String.valueOf(obj[0])) : 0);
				dto.setProductName(obj[1] != null ? String.valueOf(obj[1]) : "");
				dto.setCategoryName(obj[2] != null ? String.valueOf(obj[2]) : "");
				dto.setVendorName(obj[3] != null ? String.valueOf(obj[3]) : "");
				dto.setInStockQty(obj[4] != null ? Integer.valueOf(String.valueOf(obj[4])) : 0);
				dto.setCostPerUnit(obj[5] != null ? Double.valueOf(String.valueOf(obj[5])) : 0);
				dto.setTotalValue(obj[6] != null ? Double.valueOf(String.valueOf(obj[6])) : 0);
				dto.setEstimatedRevenue(obj[7] != null ? Double.valueOf(String.valueOf(obj[7])) : 0);
				dto.setEstimatedProfit(obj[8] != null ? Double.valueOf(String.valueOf(obj[8])) : 0);
				salesDataList.add(dto);
			}
		}
		saleResponse.setData(salesDataList);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setReport(saleResponse);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;

	}

	@Override
	public APIResponse getDeadInventoryReport(Pageable pageable, String fromDate, String toDate, Integer status,
			Integer categoryId, Integer vendorId) {

		fromDate = (fromDate != null && !fromDate.isEmpty()) ? fromDate + " 00:00:00" : null;
		toDate = (toDate != null && !toDate.isEmpty()) ? toDate + " 23:59:59" : null;

		List<Object[]> headerData = reportRepository.getDeadInventoryReportHeaderValues(fromDate, toDate, status,
				categoryId, vendorId);
		Page<Object[]> getDeadInventoryPage = reportRepository.getDeadInventoryReportData(pageable, fromDate, toDate,
				status, categoryId, vendorId);

		List<Object[]> getDeadInventoryData = getDeadInventoryPage.getContent();
		Integer totalPages = getDeadInventoryPage.getTotalPages();
		Integer pageRecords = getDeadInventoryPage.getNumberOfElements();
		Integer totalRecords = (int) getDeadInventoryPage.getTotalElements();

		ReportResDTO saleResponse = new ReportResDTO();
		if (!headerData.isEmpty()) {
			Integer inventotyStock = (headerData.get(0)[1] != null)
					? Integer.valueOf(String.valueOf(headerData.get(0)[1])) : 0;
			Double inventoryValue = (headerData.get(0)[2] != null)
					? Double.valueOf(String.valueOf(headerData.get(0)[2])) : 0.0;
			Double estimatedProfit = (headerData.get(0)[3] != null)
					? Double.valueOf(String.valueOf(headerData.get(0)[3])) : 0.0;
			saleResponse.setInventoryStocks(inventotyStock);
			saleResponse.setInventoryValue(inventoryValue);
			saleResponse.setEstimatedProfit(estimatedProfit);
		}
		List<ReportDataResDTO> salesDataList = new ArrayList<ReportDataResDTO>();
		if (!getDeadInventoryData.isEmpty()) {
			for (Object[] obj : getDeadInventoryData) {
				ReportDataResDTO dto = new ReportDataResDTO();
				dto.setProductId(obj[0] != null ? Integer.parseInt(String.valueOf(obj[0])) : 0);
				dto.setProductName(obj[1] != null ? String.valueOf(obj[1]) : "");
				dto.setCategoryName(obj[2] != null ? String.valueOf(obj[2]) : "");
				dto.setVendorName(obj[3] != null ? String.valueOf(obj[3]) : "");
				dto.setInStockQty(obj[4] != null ? Integer.valueOf(String.valueOf(obj[4])) : 0);
				dto.setCostPerUnit(obj[5] != null ? Double.valueOf(String.valueOf(obj[5])) : 0);
				dto.setTotalValue(obj[6] != null ? Double.valueOf(String.valueOf(obj[6])) : 0);
				dto.setEstimatedRevenue(obj[7] != null ? Double.valueOf(String.valueOf(obj[7])) : 0);
				dto.setEstimatedProfit(obj[8] != null ? Double.valueOf(String.valueOf(obj[8])) : 0);
				dto.setLastSaleDate(obj[9] != null ? String.valueOf(obj[9]) : "");
				salesDataList.add(dto);
			}
		}
		saleResponse.setData(salesDataList);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setReport(saleResponse);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;

	}

	@Override
	public APIResponse getLowInventoryReport(Pageable pageable, String fromDate, String toDate, Integer status,
			Integer categoryId, Integer vendorId) {

		fromDate = (fromDate != null && !fromDate.isEmpty()) ? fromDate + " 00:00:00" : null;
		toDate = (toDate != null && !toDate.isEmpty()) ? toDate + " 23:59:59" : null;

		List<Object[]> headerData = reportRepository.getLowInventoryReportHeaderValues(fromDate, toDate, status,
				categoryId, vendorId);

		Page<Object[]> lowInventoryPages = reportRepository.getLowInventoryReportData(pageable, fromDate, toDate,
				status, categoryId, vendorId);

		List<Object[]> lowInventoryData = lowInventoryPages.getContent();
		Integer totalPages = lowInventoryPages.getTotalPages();
		Integer pageRecords = lowInventoryPages.getNumberOfElements();
		Integer totalRecords = (int) lowInventoryPages.getTotalElements();

		ReportResDTO saleResponse = new ReportResDTO();
		if (!headerData.isEmpty()) {
			Integer inventotyStock = (headerData.get(0)[1] != null)
					? Integer.valueOf(String.valueOf(headerData.get(0)[1])) : 0;
			Double inventoryValue = (headerData.get(0)[2] != null)
					? Double.valueOf(String.valueOf(headerData.get(0)[2])) : 0.0;
			Double estimatedProfit = (headerData.get(0)[3] != null)
					? Double.valueOf(String.valueOf(headerData.get(0)[3])) : 0.0;
			saleResponse.setInventoryStocks(inventotyStock);
			saleResponse.setInventoryValue(inventoryValue);
			saleResponse.setEstimatedProfit(estimatedProfit);
		}
		List<ReportDataResDTO> salesDataList = new ArrayList<ReportDataResDTO>();
		if (!lowInventoryData.isEmpty()) {
			for (Object[] obj : lowInventoryData) {
				ReportDataResDTO dto = new ReportDataResDTO();
				dto.setProductId(obj[0] != null ? Integer.parseInt(String.valueOf(obj[0])) : 0);
				dto.setProductName(obj[1] != null ? String.valueOf(obj[1]) : "");
				dto.setCategoryName(obj[2] != null ? String.valueOf(obj[2]) : "");
				dto.setVendorName(obj[3] != null ? String.valueOf(obj[3]) : "");
				dto.setInStockQty(obj[4] != null ? Integer.valueOf(String.valueOf(obj[4])) : 0);
				dto.setCostPerUnit(obj[5] != null ? Double.valueOf(String.valueOf(obj[5])) : 0);
				dto.setTotalValue(obj[6] != null ? Double.valueOf(String.valueOf(obj[6])) : 0);
				dto.setEstimatedRevenue(obj[7] != null ? Double.valueOf(String.valueOf(obj[7])) : 0);
				dto.setEstimatedProfit(obj[8] != null ? Double.valueOf(String.valueOf(obj[8])) : 0);
				dto.setParLevel(obj[9] != null ? Integer.valueOf(String.valueOf(obj[9])) : 0);
				dto.setReOrderQty(obj[10] != null ? Integer.valueOf(String.valueOf(obj[10])) : 0);
				salesDataList.add(dto);
			}
		}
		saleResponse.setData(salesDataList);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setReport(saleResponse);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;

	}

	@Override
	public APIResponse getStockForeCastReport(Pageable pageable, Integer vendorId) {

		Page<Object[]> stockForeCastPage = reportRepository.getStockForeCastPage(pageable, vendorId);
		Integer totalPages = stockForeCastPage.getTotalPages();
		Integer pageRecords = stockForeCastPage.getNumberOfElements();
		Integer totalRecords = (int) stockForeCastPage.getTotalElements();

		List<Object[]> stockForeCastData = stockForeCastPage.getContent();
		ReportResDTO stockForeCastResponse = new ReportResDTO();

		List<ReportDataResDTO> stockForeCastDataList = new ArrayList<ReportDataResDTO>();
		if (!stockForeCastData.isEmpty()) {
			for (Object[] obj : stockForeCastData) {
				ReportDataResDTO dto = new ReportDataResDTO();
				dto.setProductName(obj[1] != null ? String.valueOf(obj[1]) : "");
				dto.setCategoryName(obj[2] != null ? String.valueOf(obj[2]) : "");
				dto.setVendorName(obj[3] != null ? String.valueOf(obj[3]) : "");
				dto.setSKU(obj[4] != null ? (String.valueOf(obj[4])) : "");
				dto.setStockForeCast(obj[5] != null ? (String.valueOf(obj[5])) : "");
				stockForeCastDataList.add(dto);
			}
		}
		stockForeCastResponse.setData(stockForeCastDataList);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setReport(stockForeCastResponse);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;

	}

}
