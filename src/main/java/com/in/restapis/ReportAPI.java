package com.in.restapis;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in.bean.APIResponse;
import com.in.excel.ReportExcelExporter;
import com.in.exception.ValidationException;
import com.in.service.ReportService;

@RestController
@RequestMapping("/api/report")
@CrossOrigin
public class ReportAPI {

	@Autowired
	ReportService reportService;

	// Sales Report API
	@GetMapping("/sales")
	public ResponseEntity<APIResponse> getSalesReport(HttpServletResponse response, Pageable pageable,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) Integer vendorId) throws ValidationException, IOException {

		return ResponseEntity.ok()
				.body(reportService.getSalesReport(pageable, fromDate, toDate, status, categoryId, vendorId));
	}

	@GetMapping("/sales/excel")
	public ResponseEntity<APIResponse> getSalesExcelReport(HttpServletResponse response, Pageable pageable,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) Integer vendorId) throws ValidationException, IOException {
		salesExportReport(response, pageable, "sales", "SalesData", fromDate, toDate, status, categoryId, vendorId);
		return null;
	}

	public void salesExportReport(HttpServletResponse response, Pageable pageable, String reportType, String fileName,
			String fromDate, String toDate, Integer status, Integer categoryId, Integer vendorId) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName + ".xlsx";
		response.setHeader(headerKey, headerValue);

		try {
			APIResponse reoportData = reportService.getSalesReport(pageable, fromDate, toDate, status, categoryId,
					vendorId);
			ReportExcelExporter excelExporter = new ReportExcelExporter(reoportData.getReport(), reportType);
			excelExporter.export(response);
		} catch (Exception e) {
			e.printStackTrace();
			ReportExcelExporter excelExporter = new ReportExcelExporter(null, null);
			excelExporter.export(response);
		}

	}

	// Discount Report API
	@GetMapping("/discount")
	public ResponseEntity<APIResponse> getDiscountReport(HttpServletResponse response, Pageable pageable,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate)
			throws ValidationException, IOException {
		return ResponseEntity.ok().body(reportService.getDiscountReport(pageable, fromDate, toDate));
	}

	@GetMapping("/discount/excel")
	public ResponseEntity<APIResponse> getDiscountExcelReport(HttpServletResponse response, Pageable pageable,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate)
			throws ValidationException, IOException {
		discountexportReport(response, pageable, "discount", "DiscountData", fromDate, toDate);
		return null;
	}

	public void discountexportReport(HttpServletResponse response, Pageable pageable, String reportType,
			String fileName, String fromDate, String toDate) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName + ".xlsx";
		response.setHeader(headerKey, headerValue);

		try {
			APIResponse reoportData = reportService.getDiscountReport(pageable, fromDate, toDate);
			ReportExcelExporter excelExporter = new ReportExcelExporter(reoportData.getReport(), reportType);
			excelExporter.export(response);
		} catch (Exception e) {
			e.printStackTrace();
			ReportExcelExporter excelExporter = new ReportExcelExporter(null, null);
			excelExporter.export(response);
		}

	}

	// Vendor Sales Report API
	@GetMapping("/vendor/sales")
	public ResponseEntity<APIResponse> getVendorsSalesReport(HttpServletResponse response, Pageable pageable,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) Integer vendorId) throws ValidationException, IOException {
		return ResponseEntity.ok()
				.body(reportService.getVendorsSalesReport(pageable, fromDate, toDate, status, categoryId, vendorId));
	}

	@GetMapping("/vendor/sales/excel")
	public ResponseEntity<APIResponse> getVendorsSalesExcelReport(HttpServletResponse response, Pageable pageable,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) Integer vendorId) throws ValidationException, IOException {
		vendorSalesExportReport(response, pageable, "vendorSales", "VendorSalesData", fromDate, toDate, status,
				categoryId, vendorId);
		return null;
	}

	public void vendorSalesExportReport(HttpServletResponse response, Pageable pageable, String reportType,
			String fileName, String fromDate, String toDate, Integer status, Integer categoryId, Integer vendorId)
			throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName + ".xlsx";
		response.setHeader(headerKey, headerValue);

		try {
			APIResponse reoportData = reportService.getVendorsSalesReport(pageable, fromDate, toDate, status,
					categoryId, vendorId);
			ReportExcelExporter excelExporter = new ReportExcelExporter(reoportData.getReport(), reportType);
			excelExporter.export(response);
		} catch (Exception e) {
			e.printStackTrace();
			ReportExcelExporter excelExporter = new ReportExcelExporter(null, null);
			excelExporter.export(response);
		}

	}

	// Dead Inventory Report API

	@GetMapping("/deadInventory")
	public ResponseEntity<APIResponse> getDeadInventoryReport(HttpServletResponse response, Pageable pageable,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) Integer vendorId) throws ValidationException, IOException {
		return ResponseEntity.ok()
				.body(reportService.getDeadInventoryReport(pageable, fromDate, toDate, status, categoryId, vendorId));
	}

	@GetMapping("/deadInventory/excel")
	public ResponseEntity<APIResponse> getDeadInventoryExcelReport(HttpServletResponse response, Pageable pageable,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) Integer vendorId) throws ValidationException, IOException {
		deadInventoryexportReport(response, pageable, "deadInventory", "DeadInventoryReport", fromDate, toDate, status,
				categoryId, vendorId);
		return null;
	}

	public void deadInventoryexportReport(HttpServletResponse response, Pageable pageable, String reportType,
			String fileName, String fromDate, String toDate, Integer status, Integer categoryId, Integer vendorId)
			throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName + ".xlsx";
		response.setHeader(headerKey, headerValue);

		try {
			APIResponse reoportData = reportService.getDeadInventoryReport(pageable, fromDate, toDate, status,
					categoryId, vendorId);
			ReportExcelExporter excelExporter = new ReportExcelExporter(reoportData.getReport(), reportType);
			excelExporter.export(response);
		} catch (Exception e) {
			e.printStackTrace();
			ReportExcelExporter excelExporter = new ReportExcelExporter(null, null);
			excelExporter.export(response);
		}

	}

	// Low Inventory Report API

	@GetMapping("/lowInventory")
	public ResponseEntity<APIResponse> getLowInventoryReport(HttpServletResponse response, Pageable pageable,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) Integer vendorId) throws ValidationException, IOException {
		return ResponseEntity.ok()
				.body(reportService.getLowInventoryReport(pageable, fromDate, toDate, status, categoryId, vendorId));
	}

	@GetMapping("/lowInventory/excel")
	public ResponseEntity<APIResponse> getLowInventoryExcelReport(HttpServletResponse response, Pageable pageable,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) Integer vendorId) throws ValidationException, IOException {
		lowInventoryexportReport(response, pageable, "lowInventory", "LowInventory", fromDate, toDate, status,
				categoryId, vendorId);
		return null;
	}

	public void lowInventoryexportReport(HttpServletResponse response, Pageable pageable, String reportType,
			String fileName, String fromDate, String toDate, Integer status, Integer categoryId, Integer vendorId)
			throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName + ".xlsx";
		response.setHeader(headerKey, headerValue);
		try {
			APIResponse reoportData = reportService.getLowInventoryReport(pageable, fromDate, toDate, status,
					categoryId, vendorId);
			ReportExcelExporter excelExporter = new ReportExcelExporter(reoportData.getReport(), reportType);
			excelExporter.export(response);
		} catch (Exception e) {
			e.printStackTrace();
			ReportExcelExporter excelExporter = new ReportExcelExporter(null, null);
			excelExporter.export(response);
		}

	}

	// StockForecast Report API
	@GetMapping("/stock/forecast")
	public ResponseEntity<APIResponse> getStockForeCastReport(HttpServletResponse response, Pageable pageable,
			@RequestParam(required = false) Integer vendorId) throws ValidationException, IOException {

		return ResponseEntity.ok().body(reportService.getStockForeCastReport(pageable, vendorId));
	}

	@GetMapping("/stock/forecast/excel")
	public ResponseEntity<APIResponse> getStockForeCastExcelReport(HttpServletResponse response, Pageable pageable,
			@RequestParam(required = false) Integer vendorId) throws ValidationException, IOException {
		stockForeCastExportReport(response, pageable, "StockForeCast", "StockForeCastData", vendorId);
		return null;
	}

	public void stockForeCastExportReport(HttpServletResponse response, Pageable pageable, String reportType,
			String fileName, Integer vendorId) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName + ".xlsx";
		response.setHeader(headerKey, headerValue);

		try {
			APIResponse reoportData = reportService.getStockForeCastReport(pageable, vendorId);
			ReportExcelExporter excelExporter = new ReportExcelExporter(reoportData.getReport(), reportType);
			excelExporter.export(response);
		} catch (Exception e) {
			e.printStackTrace();
			ReportExcelExporter excelExporter = new ReportExcelExporter(null, null);
			excelExporter.export(response);
		}

	}

}
