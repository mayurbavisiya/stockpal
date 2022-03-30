package com.in.restapis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.request.dto.POSOrderReqDTO;
import com.in.service.POSOrderService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class POSOrderAPI {

	@Autowired
	POSOrderService posOrderService;

	@PostMapping("/pos/order")
	public ResponseEntity<APIResponse> saveCategory(@RequestBody POSOrderReqDTO dto) throws ValidationException {
		return ResponseEntity.ok().body(posOrderService.savePOSOrder(dto));
	}

}
