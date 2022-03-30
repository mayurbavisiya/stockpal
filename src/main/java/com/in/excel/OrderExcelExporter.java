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

import com.in.response.dto.OrderDetailsByOrderIdResDTO;

public class OrderExcelExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<OrderDetailsByOrderIdResDTO> orderDetails;

	public OrderExcelExporter(List<OrderDetailsByOrderIdResDTO> orderDetails) {
		this.orderDetails = orderDetails;
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

		sheet = workbook.createSheet("Orders");

		int rowCount = 0;
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		if (orderDetails != null) {

			Row row0 = sheet.createRow(rowCount++);
			createCell(row0, 0, "Order Id", style);
			createCell(row0, 1, orderDetails.get(0).getOrderId(), style);

			Row row1 = sheet.createRow(rowCount++);
			createCell(row1, 0, "Vendor Name", style);
			createCell(row1, 1, orderDetails.get(0).getVendorName(), style);

			Row row2 = sheet.createRow(rowCount++);
			createCell(row2, 0, "Mobile", style);
			createCell(row2, 1, orderDetails.get(0).getVendorMobile(), style);

			Row row3 = sheet.createRow(rowCount++);
			createCell(row3, 0, "Address", style);
			createCell(row3, 1, orderDetails.get(0).getVendorAddress(), style);

			Row row4 = sheet.createRow(rowCount++);
			createCell(row4, 0, "Email", style);
			createCell(row4, 1, orderDetails.get(0).getVendorEmail(), style);

			Row row5 = sheet.createRow(rowCount++);
			createCell(row5, 0, "Order Date", style);
			createCell(row5, 1, orderDetails.get(0).getOrderDate(), style);

			rowCount += 5;

			Row rowHeader = sheet.createRow(rowCount++);
			CellStyle style1 = workbook.createCellStyle();
			XSSFFont font1 = workbook.createFont();
			font1.setBold(true);
			font1.setFontHeight(16);
			style1.setFont(font);

			createCell(rowHeader, 0, "Sr No", style1);
			createCell(rowHeader, 1, "Product Name", style1);
			createCell(rowHeader, 2, "Price", style1);
			createCell(rowHeader, 3, "Quantity", style1);

			// rowCount += 2;
			int temp = 1, totalPrice = 0;
			for (OrderDetailsByOrderIdResDTO obj : orderDetails) {
				Row row = sheet.createRow(rowCount++);
				int columnCount = 0;

				createCell(row, columnCount++, temp++, style);
				createCell(row, columnCount++, obj.getProductName(), style);
				createCell(row, columnCount++, "$" + obj.getProductPrice(), style);
				createCell(row, columnCount++, obj.getQty(), style);
				totalPrice += obj.getProductPrice();

			}
			rowCount += 2;
			Row row6 = sheet.createRow(rowCount++);
			createCell(row6, 0, "Gross Total", style);
			createCell(row6, 3, "$" + totalPrice, style);

		} else {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(row, columnCount++, "No Order found for this Order Id", style);
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
