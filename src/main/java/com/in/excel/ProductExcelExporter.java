package com.in.excel;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.in.response.dto.ProductResDTO;

public class ProductExcelExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<ProductResDTO> products;

	public ProductExcelExporter(List<ProductResDTO> list) {
		this.products = list;
		workbook = new XSSFWorkbook();
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
		} else if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} else {
			cell.setCellValue((String) value);
		}

		cell.setCellStyle(style);
	}

	private void writeDataLines() {

		sheet = workbook.createSheet("Invoice");

		int rowCount = 0;
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		if (products != null) {

			Row rowHeader = sheet.createRow(rowCount++);
			CellStyle style1 = workbook.createCellStyle();
			XSSFFont font1 = workbook.createFont();
			font1.setBold(true);
			font1.setFontHeight(16);
			style1.setFont(font);
			int columnCount = 0;
			createCell(rowHeader, columnCount++, "Id", style);
			createCell(rowHeader, columnCount++, "Name", style);
			createCell(rowHeader, columnCount++, "In Stock", style);
			createCell(rowHeader, columnCount++, "Selling Price", style);
			createCell(rowHeader, columnCount++, "Cost", style);
			createCell(rowHeader, columnCount++, "PAR Level", style);
			createCell(rowHeader, columnCount++, "ReOrder Qty", style);
			createCell(rowHeader, columnCount++, "Vendor Name", style);
			createCell(rowHeader, columnCount++, "Margin", style);
			createCell(rowHeader, columnCount++, "Total Variant", style);
			createCell(rowHeader, columnCount++, "EBT Enabled", style);
			createCell(rowHeader, columnCount++, "Category", style);
			createCell(rowHeader, columnCount++, "Added On", style);

			// rowCount += 2;
			for (ProductResDTO obj : products) {
				Row row = sheet.createRow(rowCount++);
				columnCount = 0;
				createCell(row, columnCount++, obj.getId(), style);
				createCell(row, columnCount++, obj.getName(), style);
				createCell(row, columnCount++, obj.getProductStockDetails().getStockQty(), style);
				createCell(row, columnCount++, "$" + obj.getProductPriceDetails().getSellingPrice(), style);
				createCell(row, columnCount++, "$" + obj.getProductPriceDetails().getCostPrice(), style);
				createCell(row, columnCount++, obj.getProductStockDetails().getParLevel(), style);
				createCell(row, columnCount++, obj.getProductStockDetails().getReorderQty(), style);
				createCell(row, columnCount++, obj.getProductPriceDetails().getVendorName(), style);
				createCell(row, columnCount++, "$" + obj.getProductPriceDetails().getMargin(), style);
				createCell(row, columnCount++, obj.getProductOtherDetails().getProductPacks().size(), style);
				createCell(row, columnCount++, obj.getProductOtherDetails().getEbtEnabled() == 0 ? false : true, style);
				createCell(row, columnCount++, obj.getCategoryName(), style);
				createCell(row, columnCount++, obj.getCreatedDate(), style);

			}
		} else {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(row, columnCount++, "No Products found", style);
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
