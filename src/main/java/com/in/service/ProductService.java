package com.in.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.request.dto.ProductPackReqDTO;
import com.in.request.dto.ProductReqDTO;
import com.in.request.dto.StockAdjustmentReqDTO;

@Service
@Transactional
public interface ProductService {

	APIResponse saveProduct(ProductReqDTO dto) throws ValidationException;

	APIResponse deleteProduct(Long id) throws ValidationException;

	APIResponse getAllProducts(Pageable pageable, String name, String fromDate, String toDate, Integer categoryId, String inventoryType)
	throws ValidationException;

	APIResponse uploadFile(MultipartFile file) throws ValidationException;

	APIResponse getProductById(Long id) throws ValidationException;

	APIResponse updateProduct(Long id, ProductReqDTO productReqDTO) throws ValidationException;

	APIResponse getProductByBarCodeId(String barcodeNo);

	APIResponse getProductByName(Pageable pageable, String name) throws ValidationException;

	APIResponse updateProductPack(Long id, ProductPackReqDTO productPackReqDTO) throws ValidationException;

	APIResponse deleteProductPack(Long id) throws ValidationException;

	APIResponse stockAdjustment(StockAdjustmentReqDTO reqDTO) throws ValidationException;

	APIResponse getStockAdjustment(Pageable pageable, Long id);

}
