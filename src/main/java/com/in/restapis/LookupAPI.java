package com.in.restapis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in.bean.APIResponse;
import com.in.service.LookupService;
import com.in.service.PriceTypesLookupService;
import com.in.service.SizeLookupService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LookupAPI {

	@Autowired
	LookupService lookupService;

	@Autowired
	SizeLookupService sizeLookupService;

	@Autowired
	PriceTypesLookupService priceTypesLookupService;

	

	@GetMapping("/days")
	public ResponseEntity<APIResponse> getAllScheduleDays() {
		return ResponseEntity.ok().body(lookupService.getAllScheduleDays());
	}

	@GetMapping("/sizes")
	public ResponseEntity<APIResponse> getAllSizes() {
		return ResponseEntity.ok().body(sizeLookupService.getAllSize());
	}

	@GetMapping("/priceTypes")
	public ResponseEntity<APIResponse> getPriceTypes() {
		return ResponseEntity.ok().body(priceTypesLookupService.getPriceTypes());
	}



}
