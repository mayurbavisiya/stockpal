package com.in.restapis;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in.bean.APIResponse;
import com.in.excel.InvoiceExcelExporter;
import com.in.exception.ValidationException;
import com.in.request.dto.InvoiceReqDTO;
import com.in.service.InvoiceService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class InvoiceAPI {

	@Autowired
	InvoiceService invoiceService;

	@PostMapping("/invoice")
	public ResponseEntity<APIResponse> saveInvoice(@RequestBody InvoiceReqDTO dto) throws ValidationException {
		return ResponseEntity.ok().body(invoiceService.saveInvoice(dto));
	}

	@GetMapping("/invoice")
	public ResponseEntity<APIResponse> getAllInvoice(Pageable pageable,
			@RequestParam(required = false, value = "fromDate") String fromDate,
			@RequestParam(required = false, value = "toDate") String toDate,
			@RequestParam(required = false, value = "id") String invoiceId) throws ValidationException {
		return ResponseEntity.ok().body(invoiceService.getAllInvoice(pageable, fromDate, toDate, invoiceId));
	}

	@GetMapping("/invoice/{id}")
	public ResponseEntity<APIResponse> getInvoiceById(HttpServletResponse response, @PathVariable(value = "id") Long id,
			@RequestParam Optional<String> excel) throws ValidationException, IOException {
		return ResponseEntity.ok().body(invoiceService.getInvoiceById(id));
	}

	@GetMapping("/invoice/excel/{id}")
	public ResponseEntity<APIResponse> getInvoiceExcelById(HttpServletResponse response,
			@PathVariable(value = "id") Long id) throws ValidationException, IOException {
		exportReport(response, id);
		return null;
	}

	@DeleteMapping("/invoice/{id}")
	public ResponseEntity<APIResponse> deleteInvoice(@PathVariable(value = "id") Long id) throws ValidationException {
		return ResponseEntity.ok().body(invoiceService.deleteInvoice(id));
	}

	public void exportReport(HttpServletResponse response, Long invoiceId) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Invoice_" + invoiceId + ".xlsx";
		response.setHeader(headerKey, headerValue);
		try {
			APIResponse invoice = invoiceService.getInvoiceById(invoiceId);
			InvoiceExcelExporter excelExporter = new InvoiceExcelExporter(invoice.getInvoices());
			excelExporter.export(response);
		} catch (Exception e) {
			InvoiceExcelExporter excelExporter = new InvoiceExcelExporter(null);
			excelExporter.export(response);
		}
	}
}
