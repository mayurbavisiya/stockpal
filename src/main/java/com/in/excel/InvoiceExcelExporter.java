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

import com.in.response.dto.InvoiceProductResDTO;
import com.in.response.dto.InvoiceResDTO;

public class InvoiceExcelExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<InvoiceResDTO> invoices;

	public InvoiceExcelExporter(List<InvoiceResDTO> invoices) {
		this.invoices = invoices;
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

		if (invoices != null) {

			Row row0 = sheet.createRow(rowCount++);
			createCell(row0, 0, "Invoice Number", style);
			createCell(row0, 1, invoices.get(0).getInvoiceNumber(), style);

			Row row1 = sheet.createRow(rowCount++);
			createCell(row1, 0, "Vendor Name", style);
			createCell(row1, 1, invoices.get(0).getVendorName(), style);

			Row row2 = sheet.createRow(rowCount++);
			createCell(row2, 0, "Order Id", style);
			createCell(row2, 1, invoices.get(0).getOrderId(), style);

			Row row3 = sheet.createRow(rowCount++);
			createCell(row3, 0, "Invoice Date", style);
			createCell(row3, 1, invoices.get(0).getInvoiceDate(), style);

			Row row4 = sheet.createRow(rowCount++);
			createCell(row4, 0, "Due Date", style);
			createCell(row4, 1, invoices.get(0).getDueDate(), style);

			rowCount += 4;

			Row rowHeader = sheet.createRow(rowCount++);
			CellStyle style1 = workbook.createCellStyle();
			XSSFFont font1 = workbook.createFont();
			font1.setBold(true);
			font1.setFontHeight(16);
			style1.setFont(font);

			createCell(rowHeader, 0, "Sr No", style1);
			createCell(rowHeader, 1, "Product Name", style1);
			createCell(rowHeader, 2, "Received Qty", style1);
			createCell(rowHeader, 3, "Cost Price", style1);
			createCell(rowHeader, 4, "Margin", style1);
			createCell(rowHeader, 5, "Selling Price", style1);

			// rowCount += 2;
			int temp = 1, receivedQty = 0;
			Double costPrice = 0.0, totalMargin = 0.0, sellingPrice = 0.0;
			for (InvoiceProductResDTO obj : invoices.get(0).getInvoiceProducts()) {
				Row row = sheet.createRow(rowCount++);
				int columnCount = 0;

				createCell(row, columnCount++, temp++, style);
				createCell(row, columnCount++, obj.getProductName(), style);
				createCell(row, columnCount++, obj.getReceivedQty(), style);
				receivedQty += obj.getReceivedQty();
				createCell(row, columnCount++, "$" + obj.getCostPrice(), style);
				costPrice += obj.getCostPrice();
				createCell(row, columnCount++, obj.getMargin() + "%", style);
				totalMargin += obj.getMargin();
				createCell(row, columnCount++, "$" + obj.getSellingPrice(), style);
				sellingPrice += obj.getSellingPrice();
			}
			rowCount += 2;
			Row row6 = sheet.createRow(rowCount++);
			createCell(row6, 0, "Gross Total", style);
			createCell(row6, 2, receivedQty, style);
			createCell(row6, 3, "$" + costPrice, style);
//			createCell(row6, 4, totalMargin + "%", style);
//			createCell(row6, 5, "$" + sellingPrice, style);

		} else {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(row, columnCount++, "No Invoice found for this Id", style);
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
