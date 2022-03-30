package com.in.restapis;

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

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.request.dto.VendorReqDTO;
import com.in.service.VendorService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class VendorAPI {

	@Autowired
	VendorService vendorService;

	@GetMapping("/vendors")
	public ResponseEntity<APIResponse> getAllVendor(Pageable pageable,
			@RequestParam(required = false, value = "name") String name) {
		return ResponseEntity.ok().body(vendorService.getAllVendor(pageable, name));
	}

	@GetMapping("/vendors/{name}")
	public ResponseEntity<APIResponse> getAllVendorByName(@PathVariable(value = "name") String name) {
		return ResponseEntity.ok().body(vendorService.getVendorContainsName(name));
	}

	@GetMapping("/vendors/id/{id}")
	public ResponseEntity<APIResponse> getvendorById(@PathVariable(value = "id") Long id) throws ValidationException {
		return ResponseEntity.ok().body(vendorService.getvendorById(id));
	}

	@PostMapping("/vendor")
	public ResponseEntity<APIResponse> createVendor(@RequestBody VendorReqDTO dto) throws ValidationException {
		return ResponseEntity.ok().body(vendorService.createVendor(dto));
	}

	@PutMapping("/vendor/{id}")
	public ResponseEntity<APIResponse> updateVendor(@PathVariable(value = "id") Long id, @RequestBody VendorReqDTO dto)
			throws ValidationException {
		return ResponseEntity.ok().body(vendorService.updateVendor(id, dto));
	}

	@DeleteMapping("/vendor/{id}")
	public ResponseEntity<APIResponse> deleteVendor(@PathVariable(value = "id") Long id) throws ValidationException {
		return ResponseEntity.ok().body(vendorService.deleteVendor(id));
	}
}
