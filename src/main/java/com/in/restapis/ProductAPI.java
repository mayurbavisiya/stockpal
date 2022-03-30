package com.in.restapis;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.in.bean.APIResponse;
import com.in.excel.ProductExcelExporter;
import com.in.exception.ValidationException;
import com.in.request.dto.ProductPackReqDTO;
import com.in.request.dto.ProductReqDTO;
import com.in.request.dto.StockAdjustmentReqDTO;
import com.in.service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductAPI {

	@Autowired
	ProductService productService;

	@PostMapping("/product")
	public ResponseEntity<APIResponse> saveProduct(@RequestBody ProductReqDTO productReqDTO)
			throws ValidationException {
		return ResponseEntity.ok().body(productService.saveProduct(productReqDTO));
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<APIResponse> deleteProduct(@PathVariable(value = "id") Long id) throws ValidationException {
		return ResponseEntity.ok().body(productService.deleteProduct(id));
	}

	@PutMapping("/product/{id}")
	public ResponseEntity<APIResponse> updateProduct(@PathVariable(value = "id") Long id,
			@RequestBody ProductReqDTO productReqDTO) throws ValidationException {
		return ResponseEntity.ok().body(productService.updateProduct(id, productReqDTO));
	}

	@GetMapping("/products")
	public ResponseEntity<APIResponse> getAllProducts(HttpServletResponse response, Pageable pageable,
			@RequestParam(required = false) String name, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate, @RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) String inventoryType) throws ValidationException, IOException {

		return ResponseEntity.ok()
				.body(productService.getAllProducts(pageable, name, fromDate, toDate, categoryId, inventoryType));
	}

	@GetMapping("/products/excel")
	public ResponseEntity<APIResponse> getAllProductsByExcel(HttpServletResponse response, Pageable pageable,
			@RequestParam(required = false) String name, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate, @RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) String inventoryType) throws ValidationException, IOException {

		exportReport(response, pageable, name, fromDate, toDate, categoryId, inventoryType);
		return null;
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<APIResponse> getProductById(@PathVariable(value = "id") Long id) throws ValidationException {
		return ResponseEntity.ok().body(productService.getProductById(id));
	}

	@GetMapping("/product/name/{name}")
	public ResponseEntity<APIResponse> getProductByName(Pageable pageable, @PathVariable(value = "name") String name)
			throws ValidationException {
		return ResponseEntity.ok().body(productService.getProductByName(pageable, name));
	}

	@GetMapping("/product/barcode/{id}")
	public ResponseEntity<APIResponse> getProductByBarCodeId(@PathVariable(value = "id") String barcodeNo)
			throws ValidationException {
		return ResponseEntity.ok().body(productService.getProductByBarCodeId(barcodeNo));
	}

	@PostMapping("/product/uploadFile")
	public ResponseEntity<APIResponse> uploadFile(@RequestParam("file") MultipartFile file) throws ValidationException {
		return ResponseEntity.ok().body(productService.uploadFile(file));
	}

	@PutMapping("/product/pack/{id}")
	public ResponseEntity<APIResponse> updateProductPack(@PathVariable(value = "id") Long id,
			@RequestBody ProductPackReqDTO productPackReqDTO) throws ValidationException {
		return ResponseEntity.ok().body(productService.updateProductPack(id, productPackReqDTO));
	}

	@DeleteMapping("/product/pack/{id}")
	public ResponseEntity<APIResponse> deleteProductPack(@PathVariable(value = "id") Long id)
			throws ValidationException {
		return ResponseEntity.ok().body(productService.deleteProductPack(id));
	}

	@PostMapping("/product/stock/adjust")
	public ResponseEntity<APIResponse> stockAdjustment(@RequestBody StockAdjustmentReqDTO reqDTO)
			throws ValidationException {
		return ResponseEntity.ok().body(productService.stockAdjustment(reqDTO));
	}

	@GetMapping("/product/stock/adjust/{id}")
	public ResponseEntity<APIResponse> getStockAdjustment(Pageable pageable, @PathVariable(value = "id") Long id)
			throws ValidationException {
		return ResponseEntity.ok().body(productService.getStockAdjustment(pageable, id));
	}

	public void exportReport(HttpServletResponse response, Pageable pageable, String name, String fromDate,
			String toDate, Integer categoryId, String inventoryType) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Product_Data.xlsx";
		response.setHeader(headerKey, headerValue);
		try {
			APIResponse apiResponse = productService.getAllProducts(null, name, fromDate, toDate, categoryId,
					inventoryType);
			ProductExcelExporter excelExporter = new ProductExcelExporter(apiResponse.getProducts());
			excelExporter.export(response);
		} catch (Exception e) {
			ProductExcelExporter excelExporter = new ProductExcelExporter(null);
			excelExporter.export(response);
		}
	}
}
