package com.in.serviceimpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.repository.DashBoardRepository;
import com.in.response.dto.DashBoardBestSellerResDTO;
import com.in.response.dto.DashBoardGraphSaleDataResDTO;
import com.in.response.dto.DashBoardNewProductResDTO;
import com.in.response.dto.DashBoardPriceChangeResDTO;
import com.in.response.dto.DashBoardResDTO;
import com.in.response.dto.DashBoardTopCategoryResDTO;
import com.in.service.DashBoardService;

@Transactional
@Service
public class DashBoardServiceImpl implements DashBoardService {
	@Autowired
	DashBoardRepository dashBoardRepository;

	@Override
	public APIResponse getDashBoard(String filter, String fromDate, String toDate) {

		if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
			fromDate = fromDate + " 00:00:00";
			toDate = toDate + " 23:59:59";

		} else {
			fromDate = null;
			toDate = null;

			LocalDate today = LocalDate.now();
			String todayDate = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

			if (filter != null && !"".equals(filter)) {
				if (filter.equalsIgnoreCase("today")) {
					fromDate = todayDate + " 00:00:00";
					toDate = todayDate + " 23:59:59";
				} else if (filter.equalsIgnoreCase("yesterday")) {
					LocalDate yesterday = today.minusDays(1);
					String yesterdayDate = yesterday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					fromDate = yesterdayDate + " 00:00:00";
					toDate = todayDate + " 23:59:59";
				} else if (filter.equalsIgnoreCase("week")) {
					LocalDate lastWeek = today.minusDays(7);

					String weekDate = lastWeek.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					fromDate = weekDate + " 00:00:00";
					toDate = todayDate + " 23:59:59";
				} else if (filter.equalsIgnoreCase("month")) {
					LocalDate month = today.minusMonths(1);
					String monthDate = month.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					fromDate = monthDate + " 00:00:00";
					toDate = todayDate + " 23:59:59";
				} else {
					fromDate = null;
					toDate = null;
				}
			} else {
				fromDate = null;
				toDate = null;
			}
		}
		List<Object[]> headerData = dashBoardRepository.getDashBoardHeaderData(fromDate, toDate);
		List<Object[]> newProducts = dashBoardRepository.getNewProducts(fromDate, toDate);
		List<Object[]> priceChange = dashBoardRepository.getPriceChangeData(fromDate, toDate);
		List<Object[]> bestSeller = dashBoardRepository.getBestSeller(fromDate, toDate);
		List<Object[]> bestCategory = dashBoardRepository.getBestCategory(fromDate, toDate);

		DashBoardResDTO res = new DashBoardResDTO();
		if (!headerData.isEmpty()) {
			res.setSales(headerData.get(0)[0] != null ? Double.valueOf(String.valueOf(headerData.get(0)[0])) : 0);
			res.setNetProfit(headerData.get(0)[1] != null ? Double.valueOf(String.valueOf(headerData.get(0)[1])) : 0);
			res.setTransaction(headerData.get(0)[2] != null ? Double.valueOf(String.valueOf(headerData.get(0)[2])) : 0);
			res.setActiveInventory(
					headerData.get(0)[3] != null ? Integer.valueOf(String.valueOf(headerData.get(0)[3])) : 0);
			res.setLowInventory(
					headerData.get(0)[4] != null ? Integer.valueOf(String.valueOf(headerData.get(0)[4])) : 0);
			res.setOutOfStock(headerData.get(0)[5] != null ? Integer.valueOf(String.valueOf(headerData.get(0)[5])) : 0);
		}
		List<DashBoardNewProductResDTO> newProductList = new ArrayList<DashBoardNewProductResDTO>();
		if (!newProducts.isEmpty()) {
			for (Object[] obj : newProducts) {
				DashBoardNewProductResDTO dto = new DashBoardNewProductResDTO();
				dto.setName(obj[0] != null ? (String.valueOf(obj[0])) : "");
				dto.setInStock(obj[1] != null ? Integer.parseInt(String.valueOf(obj[1])) : 0);
				dto.setPrice(obj[2] != null ? Double.valueOf(String.valueOf(obj[2])) : 0);
				dto.setProductId(obj[3] != null ? Integer.valueOf(String.valueOf(obj[3])) : 0);
				newProductList.add(dto);
			}
		}
		List<DashBoardPriceChangeResDTO> priceChnageList = new ArrayList<DashBoardPriceChangeResDTO>();
		if (!priceChange.isEmpty()) {
			for (Object[] obj : priceChange) {
				DashBoardPriceChangeResDTO dto = new DashBoardPriceChangeResDTO();
				dto.setName(obj[0] != null ? (String.valueOf(obj[0])) : "");
				dto.setOldPrice(obj[1] != null ? Double.valueOf(String.valueOf(obj[1])) : 0);
				dto.setNewPrice(obj[2] != null ? Double.valueOf(String.valueOf(obj[2])) : 0);
				dto.setProductId(obj[3] != null ? Integer.valueOf(String.valueOf(obj[3])) : 0);
				priceChnageList.add(dto);
			}
		}

		List<DashBoardBestSellerResDTO> bestSellerList = new ArrayList<DashBoardBestSellerResDTO>();
		if (!bestSeller.isEmpty()) {
			for (Object[] obj : bestSeller) {
				DashBoardBestSellerResDTO dto = new DashBoardBestSellerResDTO();
				dto.setName(obj[0] != null ? (String.valueOf(obj[0])) : "");
				dto.setQtySold(obj[1] != null ? Integer.valueOf(String.valueOf(obj[1])) : 0);
				dto.setSales(obj[2] != null ? Double.valueOf(String.valueOf(obj[2])) : 0);
				dto.setProductId(obj[3] != null ? Integer.valueOf(String.valueOf(obj[3])) : 0);
				bestSellerList.add(dto);
			}
		}

		List<DashBoardTopCategoryResDTO> topCatList = new ArrayList<DashBoardTopCategoryResDTO>();
		if (!bestCategory.isEmpty()) {
			for (Object[] obj : bestCategory) {
				DashBoardTopCategoryResDTO dto = new DashBoardTopCategoryResDTO();
				dto.setName(obj[0] != null ? (String.valueOf(obj[0])) : "");
				dto.setQtySold(obj[1] != null ? Integer.valueOf(String.valueOf(obj[1])) : 0);
				dto.setTotalSales(obj[2] != null ? Double.valueOf(String.valueOf(obj[2])) : 0);
				dto.setCategoryId(obj[3] != null ? Integer.valueOf(String.valueOf(obj[3])) : 0);
				topCatList.add(dto);
			}
		}

		res.setBestSeller(bestSellerList);
		res.setNewProductData(newProductList);
		res.setPriceChangesData(priceChnageList);
		res.setTopCategory(topCatList);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		if (bestSellerList == null || bestSellerList.isEmpty())
			response.setCommonMessage("There are no product to show here");
		response.setDashBoardResponse(res);
		return response;

	}

	@Override
	public APIResponse getSaleDataInGraph(String filter, String fromDate, String toDate, Integer vendorId) {
		if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
			fromDate = fromDate + " 00:00:00";
			toDate = toDate + " 23:59:59";

		} else {
			fromDate = null;
			toDate = null;

			LocalDate today = LocalDate.now();
			String todayDate = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

			if (filter != null && !"".equals(filter)) {
				if (filter.equalsIgnoreCase("today")) {
					fromDate = todayDate + " 00:00:00";
					toDate = todayDate + " 23:59:59";
				} else if (filter.equalsIgnoreCase("yesterday")) {
					LocalDate yesterday = today.minusDays(1);
					String yesterdayDate = yesterday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					fromDate = yesterdayDate + " 00:00:00";
					toDate = todayDate + " 23:59:59";
				} else if (filter.equalsIgnoreCase("week")) {
					LocalDate lastWeek = today.minusDays(7);

					String weekDate = lastWeek.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					fromDate = weekDate + " 00:00:00";
					toDate = todayDate + " 23:59:59";
				} else if (filter.equalsIgnoreCase("month")) {
					LocalDate month = today.minusMonths(1);
					String monthDate = month.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					fromDate = monthDate + " 00:00:00";
					toDate = todayDate + " 23:59:59";
				} else {
					fromDate = null;
					toDate = null;
				}
			} else {
				fromDate = null;
				toDate = null;
			}
		}
		String[] hour = new String[24];
		Object[] sale = new Object[24];
		Arrays.fill(sale, 0);
		for (int i = 0; i < hour.length; i++) {
			hour[i] = i + 1 + ":00";
		}
		List<Object[]> saleData = dashBoardRepository.getSaleDataInGraph(fromDate, toDate, vendorId);
		DashBoardGraphSaleDataResDTO dto = new DashBoardGraphSaleDataResDTO();
		if (!saleData.isEmpty()) {
			for (Object[] obj : saleData) {
				int index = Integer.valueOf(String.valueOf(obj[0]));
				if (index == 0)
					index = 12;
				sale[index - 1] = Integer.valueOf(String.valueOf(obj[1]));
			}
		}
		dto.setData(sale);
		dto.setHour(hour);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setGraphData(dto);
		return response;
	}

	@Override
	public APIResponse getTransactionDataInGraph(String filter) {

		String fromDate = null, toDate = null;

		LocalDate today = LocalDate.now();
		String todayDate = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		if (filter != null && !"".equals(filter)) {
			if (filter.equalsIgnoreCase("today")) {
				fromDate = todayDate + " 00:00:00";
				toDate = todayDate + " 23:59:59";
			} else if (filter.equalsIgnoreCase("yesterday")) {
				LocalDate yesterday = today.minusDays(1);
				String yesterdayDate = yesterday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				fromDate = yesterdayDate + " 00:00:00";
				toDate = todayDate + " 23:59:59";
			} else if (filter.equalsIgnoreCase("week")) {
				LocalDate lastWeek = today.minusDays(7);

				String weekDate = lastWeek.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				fromDate = weekDate + " 00:00:00";
				toDate = todayDate + " 23:59:59";
			} else if (filter.equalsIgnoreCase("month")) {
				LocalDate month = today.minusMonths(1);
				String monthDate = month.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				fromDate = monthDate + " 00:00:00";
				toDate = todayDate + " 23:59:59";
			} else {
				fromDate = null;
				toDate = null;
			}
		} else {
			fromDate = null;
			toDate = null;
		}
		String[] hour = new String[24];
		Object[] transaction = new Object[24];
		Arrays.fill(transaction, 0.0);
		for (int i = 0; i < hour.length; i++) {
			hour[i] = i + 1 + ":00";
		}
		List<Object[]> saleData = dashBoardRepository.getTransactionDataInGraph(fromDate, toDate);
		DashBoardGraphSaleDataResDTO dto = new DashBoardGraphSaleDataResDTO();
		if (!saleData.isEmpty()) {
			for (Object[] obj : saleData) {
				int index = Integer.valueOf(String.valueOf(obj[0]));
				if (index == 0)
					index = 12;
				transaction[index - 1] = Double.valueOf(String.valueOf(obj[1]));
			}
		}
		dto.setData(transaction);
		dto.setHour(hour);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setGraphData(dto);
		return response;

	}

}
