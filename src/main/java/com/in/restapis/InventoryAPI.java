package com.in.restapis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.request.dto.InventoryReqDTO;
import com.in.service.InventoryService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class InventoryAPI {

	@Autowired
	InventoryService inventoryService;

	@PostMapping("/receive/inventory")
	public ResponseEntity<APIResponse> receiveInventory(@RequestBody InventoryReqDTO dto) throws ValidationException {
		return ResponseEntity.ok().body(inventoryService.receiveInventory(dto));
	}

	@PutMapping("/update/inventory")
	public ResponseEntity<APIResponse> updateInventory(@RequestBody InventoryReqDTO dto) throws ValidationException {
		return ResponseEntity.ok().body(inventoryService.updateInventory(dto));
	}

}
