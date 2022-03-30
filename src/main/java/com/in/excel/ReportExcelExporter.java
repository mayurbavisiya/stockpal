package com.in.excel;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.in.response.dto.ReportDataResDTO;
import com.in.response.dto.ReportResDTO;

public class ReportExcelExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private ReportResDTO reportData;
	private String reportType;

	public ReportExcelExporter(ReportResDTO reportResDTO, String reportType) {
		this.reportData = reportResDTO;
		workbook = new XSSFWorkbook();
		this.reportType = reportType;
	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private void writeDataLines() {

		if (reportData != null) {
			if ("sales".equals(reportType)) {
				sheet = workbook.createSheet("Sales");

				int rowCount = 0, columnCount = 0;
				CellStyle style = workbook.createCellStyle();
				XSSFFont font = workbook.createFont();
				font.setFontHeight(14);
				style.setFont(font);

				Row row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Total Sales", style);
				createCell(row, columnCount++, "$" + reportData.getTotalSales(), style);

				columnCount = 0;
				row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Product Sold", style);
				createCell(row, columnCount++, reportData.getProductSold(), style);

				columnCount = 0;
				row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Net Profit", style);
				createCell(row, columnCount++, "$" + reportData.getNetProfit(), style);

				rowCount += 4;
				columnCount = 0;

				row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Product", style);
				createCell(row, columnCount++, "Category", style);
				createCell(row, columnCount++, "Vendor", style);
				createCell(row, columnCount++, "Qty Sold", style);
				createCell(row, columnCount++, "Total Price", style);
				createCell(row, columnCount++, "Cost of Goods Sold", style);
				createCell(row, columnCount++, "Total Profit", style);

				rowCount += 1;
				columnCount = 0;
				for (ReportDataResDTO obj : reportData.getData()) {
					row = sheet.createRow(rowCount++);
					createCell(row, columnCount++, obj.getProductName(), style);
					createCell(row, columnCount++, obj.getCategoryName(), style);
					createCell(row, columnCount++, obj.getVendorName(), style);
					createCell(row, columnCount++, obj.getQtySold(), style);
					createCell(row, columnCount++, "$" + obj.getTotalPrice(), style);
					createCell(row, columnCount++, "$" + obj.getCostOfGoodSold(), style);
					createCell(row, columnCount++, "$" + obj.getTotalProfit(), style);
					columnCount = 0;
				}

			} else if ("discount".equals(reportType)) {
				sheet = workbook.createSheet("Discount");

				int rowCount = 0, columnCount = 0;
				CellStyle style = workbook.createCellStyle();
				XSSFFont font = workbook.createFont();
				font.setFontHeight(14);
				style.setFont(font);

				Row row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Product", style);
				createCell(row, columnCount++, "Category", style);
				createCell(row, columnCount++, "Vendor", style);
				createCell(row, columnCount++, "Qty Sold", style);
				createCell(row, columnCount++, "Total Price", style);
				createCell(row, columnCount++, "Cost of Goods Sold", style);
				createCell(row, columnCount++, "Discounts", style);
				createCell(row, columnCount++, "Total Profit", style);

				rowCount += 1;
				columnCount = 0;
				int qtySolds = 0;
				Double totalPrice = 0.0, costOfGoodSolds = 0.0, discounts = 0.0, totalprofit = 0.0;
				for (ReportDataResDTO obj : reportData.getData()) {
					row = sheet.createRow(rowCount++);
					createCell(row, columnCount++, obj.getProductName(), style);
					createCell(row, columnCount++, obj.getCategoryName(), style);
					createCell(row, columnCount++, obj.getVendorName(), style);
					createCell(row, columnCount++, obj.getQtySold(), style);
					qtySolds += obj.getQtySold() == null ? 0 : obj.getQtySold();
					createCell(row, columnCount++, "$" + obj.getTotalPrice(), style);
					totalPrice += obj.getTotalPrice() == null ? 0.0 : obj.getTotalPrice();
					createCell(row, columnCount++, "$" + obj.getCostOfGoodSold(), style);
					costOfGoodSolds += obj.getCostOfGoodSold() == null ? 0.0 : obj.getCostOfGoodSold();
					createCell(row, columnCount++, "$" + obj.getDiscount(), style);
					discounts += obj.getDiscount() == null ? 0.0 : obj.getDiscount();
					createCell(row, columnCount++, "$" + obj.getTotalProfit(), style);
					totalprofit += obj.getTotalProfit() == null ? 0.0 : obj.getTotalProfit();
					columnCount = 0;
				}

				rowCount += 2;
				columnCount = 3;
				row = sheet.createRow(rowCount++);
				createCell(row, 0, "Total", style);
				createCell(row, columnCount++, qtySolds, style);
				createCell(row, columnCount++, "$" + totalPrice, style);
				createCell(row, columnCount++, "$" + costOfGoodSolds, style);
				createCell(row, columnCount++, "$" + discounts, style);
				createCell(row, columnCount++, "$" + totalprofit, style);

			}

			else if ("vendorSales".equals(reportType)) {
				sheet = workbook.createSheet("Vendor Sales");

				int rowCount = 0, columnCount = 0;
				CellStyle style = workbook.createCellStyle();
				XSSFFont font = workbook.createFont();
				font.setFontHeight(14);
				style.setFont(font);

				Row row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Inventory In Stock", style);
				createCell(row, columnCount++, reportData.getInventoryStocks(), style);

				columnCount = 0;
				row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Inventory Value", style);
				createCell(row, columnCount++, "$" + reportData.getInventoryValue(), style);

				columnCount = 0;
				row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Estimated Profit", style);
				createCell(row, columnCount++, "$" + reportData.getEstimatedProfit(), style);

				rowCount += 4;
				columnCount = 0;

				row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Product", style);
				createCell(row, columnCount++, "Category", style);
				createCell(row, columnCount++, "Vendor", style);
				createCell(row, columnCount++, "In Stock Qty", style);
				createCell(row, columnCount++, "Cost/unit", style);
				createCell(row, columnCount++, "Total Value", style);
				createCell(row, columnCount++, "Estimated Revenue", style);
				createCell(row, columnCount++, "Estimated Profit", style);

				rowCount += 1;
				columnCount = 0;
				int inStockQty = 0;
				Double costUnit = 0.0, totalValue = 0.0, estimatedRevenue = 0.0, estimatedProfit = 0.0;
				for (ReportDataResDTO obj : reportData.getData()) {
					row = sheet.createRow(rowCount++);
					createCell(row, columnCount++, obj.getProductName(), style);
					createCell(row, columnCount++, obj.getCategoryName(), style);
					createCell(row, columnCount++, obj.getVendorName(), style);
					createCell(row, columnCount++, obj.getInStockQty(), style);
					inStockQty += obj.getInStockQty() == null ? 0 : obj.getInStockQty();
					createCell(row, columnCount++, "$" + obj.getCostPerUnit(), style);
					costUnit += obj.getCostPerUnit() == null ? 0.0 : obj.getCostPerUnit();
					createCell(row, columnCount++, "$" + obj.getTotalValue(), style);
					totalValue += obj.getTotalValue() == null ? 0.0 : obj.getTotalValue();
					createCell(row, columnCount++, "$" + obj.getEstimatedRevenue(), style);
					estimatedRevenue += obj.getEstimatedRevenue() == null ? 0.0 : obj.getEstimatedRevenue();
					createCell(row, columnCount++, "$" + obj.getEstimatedProfit(), style);
					estimatedProfit += obj.getEstimatedProfit() == null ? 0.0 : obj.getEstimatedProfit();
					columnCount = 0;
				}
				rowCount += 2;
				columnCount = 3;
				row = sheet.createRow(rowCount++);
				createCell(row, 0, "Total", style);
				createCell(row, columnCount++, inStockQty, style);
				createCell(row, columnCount++, "$" + costUnit, style);
				createCell(row, columnCount++, "$" + totalValue, style);
				createCell(row, columnCount++, "$" + estimatedRevenue, style);
				createCell(row, columnCount++, "$" + estimatedProfit, style);
			} else if ("deadInventory".equals(reportType)) {

				sheet = workbook.createSheet("Dead Inventory");

				int rowCount = 0, columnCount = 0;
				CellStyle style = workbook.createCellStyle();
				XSSFFont font = workbook.createFont();
				font.setFontHeight(14);
				style.setFont(font);

				Row row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Inventory In Stock", style);
				createCell(row, columnCount++, reportData.getInventoryStocks(), style);

				columnCount = 0;
				row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Inventory Value", style);
				createCell(row, columnCount++, "$" + reportData.getInventoryValue(), style);

				columnCount = 0;
				row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Estimated Profit", style);
				createCell(row, columnCount++, "$" + reportData.getEstimatedProfit(), style);

				rowCount += 4;
				columnCount = 0;

				row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Product", style);
				createCell(row, columnCount++, "Category", style);
				createCell(row, columnCount++, "Vendor", style);
				createCell(row, columnCount++, "Last Sale", style);
				createCell(row, columnCount++, "In Stock Qty", style);
				createCell(row, columnCount++, "Cost/Unit", style);
				createCell(row, columnCount++, "Total Value", style);
				createCell(row, columnCount++, "Estimated Revenue", style);
				createCell(row, columnCount++, "Estimated Profit", style);

				rowCount += 1;
				columnCount = 0;
				int inStockQty = 0;
				Double costUnit = 0.0, totalValue = 0.0, estimatedRevenue = 0.0, estimatedProfit = 0.0;
				for (ReportDataResDTO obj : reportData.getData()) {
					row = sheet.createRow(rowCount++);
					createCell(row, columnCount++, obj.getProductName(), style);
					createCell(row, columnCount++, obj.getCategoryName(), style);
					createCell(row, columnCount++, obj.getVendorName(), style);
					createCell(row, columnCount++, obj.getLastSaleDate(), style);
					createCell(row, columnCount++, obj.getInStockQty(), style);
					inStockQty += obj.getInStockQty() == null ? 0 : obj.getInStockQty();
					createCell(row, columnCount++, "$" + obj.getCostPerUnit(), style);
					costUnit += obj.getCostPerUnit() == null ? 0.0 : obj.getCostPerUnit();
					createCell(row, columnCount++, "$" + obj.getTotalValue(), style);
					totalValue += obj.getTotalValue() == null ? 0.0 : obj.getTotalValue();
					createCell(row, columnCount++, "$" + obj.getEstimatedRevenue(), style);
					estimatedRevenue += obj.getEstimatedRevenue() == null ? 0.0 : obj.getEstimatedRevenue();
					createCell(row, columnCount++, "$" + obj.getEstimatedProfit(), style);
					estimatedProfit += obj.getEstimatedProfit() == null ? 0.0 : obj.getEstimatedProfit();
					columnCount = 0;
				}
				rowCount += 2;
				columnCount = 4;
				row = sheet.createRow(rowCount++);
				createCell(row, 0, "Total", style);
				createCell(row, columnCount++, inStockQty, style);
				createCell(row, columnCount++, "$" + costUnit, style);
				createCell(row, columnCount++, "$" + totalValue, style);
				createCell(row, columnCount++, "$" + estimatedRevenue, style);
				createCell(row, columnCount++, "$" + estimatedProfit, style);

			} else if ("lowInventory".equals(reportType)) {

				sheet = workbook.createSheet("Low Inventory");

				int rowCount = 0, columnCount = 0;
				CellStyle style = workbook.createCellStyle();
				XSSFFont font = workbook.createFont();
				font.setFontHeight(14);
				style.setFont(font);

				Row row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Inventory In Stock", style);
				createCell(row, columnCount++, reportData.getInventoryStocks(), style);

				columnCount = 0;
				row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Inventory Value", style);
				createCell(row, columnCount++, "$" + reportData.getInventoryValue(), style);

				columnCount = 0;
				row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Estimated Profit", style);
				createCell(row, columnCount++, "$" + reportData.getEstimatedProfit(), style);

				rowCount += 4;
				columnCount = 0;

				row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Product", style);
				createCell(row, columnCount++, "Category", style);
				createCell(row, columnCount++, "Vendor", style);
				createCell(row, columnCount++, "In Stock Qty", style);
				createCell(row, columnCount++, "PAR Level", style);
				createCell(row, columnCount++, "Reorder Qty", style);
				createCell(row, columnCount++, "Cost/Unit", style);
				createCell(row, columnCount++, "Total Value", style);
				createCell(row, columnCount++, "Price", style);
				createCell(row, columnCount++, "Estimated Revenue", style);
				createCell(row, columnCount++, "Estimated Profit", style);

				rowCount += 1;
				columnCount = 0;
				int inStockQty = 0;
				Double costUnit = 0.0, totalValue = 0.0, price = 0.0, estimatedRevenue = 0.0, estimatedProfit = 0.0;
				for (ReportDataResDTO obj : reportData.getData()) {
					row = sheet.createRow(rowCount++);
					createCell(row, columnCount++, obj.getProductName(), style);
					createCell(row, columnCount++, obj.getCategoryName(), style);
					createCell(row, columnCount++, obj.getVendorName(), style);
					createCell(row, columnCount++, obj.getInStockQty(), style);
					inStockQty += obj.getInStockQty() == null ? 0 : obj.getInStockQty();
					createCell(row, columnCount++, obj.getParLevel(), style);
					createCell(row, columnCount++, obj.getReOrderQty(), style);
					createCell(row, columnCount++, "$" + obj.getCostPerUnit(), style);
					costUnit += obj.getCostPerUnit() == null ? 0.0 : obj.getCostPerUnit();
					createCell(row, columnCount++, "$" + obj.getTotalValue(), style);
					totalValue += obj.getTotalValue() == null ? 0.0 : obj.getTotalValue();
					createCell(row, columnCount++, "$" + obj.getTotalPrice(), style);
					price += obj.getTotalPrice() == null ? 0.0 : obj.getTotalPrice();
					createCell(row, columnCount++, "$" + obj.getEstimatedRevenue(), style);
					estimatedRevenue += obj.getEstimatedRevenue() == null ? 0.0 : obj.getEstimatedRevenue();
					createCell(row, columnCount++, "$" + obj.getEstimatedProfit(), style);
					estimatedProfit += obj.getEstimatedProfit() == null ? 0.0 : obj.getEstimatedProfit();
					columnCount = 0;
				}
				rowCount += 2;
				columnCount = 3;
				row = sheet.createRow(rowCount++);
				createCell(row, 0, "Total", style);
				createCell(row, columnCount++, inStockQty, style);
				columnCount += 2;
				createCell(row, columnCount++, "$" + costUnit, style);
				createCell(row, columnCount++, "$" + totalValue, style);
				createCell(row, columnCount++, "$" + price, style);
				createCell(row, columnCount++, "$" + estimatedRevenue, style);
				createCell(row, columnCount++, "$" + estimatedProfit, style);

			} else if ("StockForeCast".equals(reportType)) {

				sheet = workbook.createSheet("Stock ForeCast");

				int rowCount = 0, columnCount = 0;
				CellStyle style = workbook.createCellStyle();
				XSSFFont font = workbook.createFont();
				font.setFontHeight(14);
				style.setFont(font);

				Row row = sheet.createRow(rowCount++);
				createCell(row, columnCount++, "Product", style);
				createCell(row, columnCount++, "Category", style);
				createCell(row, columnCount++, "Vendor", style);
				createCell(row, columnCount++, "SKU", style);
				createCell(row, columnCount++, "Stock ForeCast", style);

				rowCount += 1;
				columnCount = 0;
				for (ReportDataResDTO obj : reportData.getData()) {
					row = sheet.createRow(rowCount++);
					createCell(row, columnCount++, obj.getProductName(), style);
					createCell(row, columnCount++, obj.getCategoryName(), style);
					createCell(row, columnCount++, obj.getVendorName(), style);
					createCell(row, columnCount++, obj.getSKU(), style);
					createCell(row, columnCount++, obj.getStockForeCast(), style);
					columnCount = 0;
				}

			}

		}
	}

	public void export(HttpServletResponse response) throws IOException {
		// writeHeaderLine();
		writeDataLines();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}
}
