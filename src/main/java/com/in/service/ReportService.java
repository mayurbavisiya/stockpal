package com.in.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;

@Service
public interface ReportService {

	APIResponse getSalesReport(Pageable pageable, String fromDate, String toDate, Integer status, Integer categoryId,
			Integer vendorId);

	APIResponse getDiscountReport(Pageable pageable, String fromDate, String toDate);

	APIResponse getVendorsSalesReport(Pageable pageable, String fromDate, String toDate, Integer status,
			Integer categoryId, Integer vendorId);

	APIResponse getDeadInventoryReport(Pageable pageable, String fromDate, String toDate, Integer status,
			Integer categoryId, Integer vendorId);

	APIResponse getLowInventoryReport(Pageable pageable, String fromDate, String toDate, Integer status,
			Integer categoryId, Integer vendorId);

	APIResponse getStockForeCastReport(Pageable pageable, Integer vendorId);

}
