package com.in.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.entity.ProductEntity;
import com.in.exception.ValidationException;
import com.in.repository.InventoryRepository;
import com.in.repository.ProductRepository;
import com.in.request.dto.InventoryPackReqDTO;
import com.in.request.dto.InventoryReqDTO;
import com.in.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	InventoryRepository inventoryRepository;

	@Autowired
	ProductRepository productRepository;

	@Override
	public APIResponse receiveInventory(InventoryReqDTO dto) throws ValidationException {
		ProductEntity product = productRepository.findById(dto.getProductId()).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "ProductNotFoundDesc", "successBooleanFalse"));

		if (dto.getSingleQty() != null && dto.getSingleQty() > 0) {
			// Add into existing quantity in product_stock table for respective
			// productId
			inventoryRepository.addProductStockByProductId(Integer.valueOf(product.getId().intValue()),
					dto.getSingleQty());
		}

		if (dto.getPacks() != null) {
			for (InventoryPackReqDTO packDTo : dto.getPacks()) {
				if (packDTo.getPackId() != null && packDTo.getPackId() > 0 && packDTo.getQty() != null
						&& packDTo.getQty() > 0) {
					// Add into existing quantity in product_pack table for
					// respective
					// pack_id
					inventoryRepository.addProductPackByPackId(Integer.valueOf(dto.getProductId().intValue()),
							packDTo.getPackId(), packDTo.getQty());
				}
			}
		}
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse updateInventory(InventoryReqDTO dto) throws ValidationException {

		ProductEntity product = productRepository.findById(dto.getProductId()).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "ProductNotFoundDesc", "successBooleanFalse"));

		if (dto.getSingleQty() != null && dto.getSingleQty() > 0) {
			// Add into existing quantity in product_stock table for respective
			// productId
			inventoryRepository.updateProductStockByProductId(Integer.valueOf(product.getId().intValue()),
					dto.getSingleQty());
		}

		if (dto.getPacks() != null) {
			for (InventoryPackReqDTO packDTo : dto.getPacks()) {
				if (packDTo.getPackId() != null && packDTo.getPackId() > 0 && packDTo.getQty() != null
						&& packDTo.getQty() > 0) {
					// Add into existing quantity in product_pack table for
					// respective
					// pack_id
					inventoryRepository.updateProductPackByPackId(Integer.valueOf(dto.getProductId().intValue()),
							packDTo.getPackId(), packDTo.getQty());
				}
			}
		}
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	
	}

}
