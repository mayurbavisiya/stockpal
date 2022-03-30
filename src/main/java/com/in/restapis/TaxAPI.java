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
import org.springframework.web.bind.annotation.RestController;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.request.dto.TaxRequestDTO;
import com.in.service.TaxService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class TaxAPI {

	@Autowired
	TaxService taxService;

	@PostMapping("/tax")
	public ResponseEntity<APIResponse> saveTax(@RequestBody TaxRequestDTO dto) throws ValidationException {
		return ResponseEntity.ok().body(taxService.saveTax(dto));
	}

	@GetMapping("/tax")
	public ResponseEntity<APIResponse> getAllTax(Pageable pageable) throws ValidationException {
		return ResponseEntity.ok().body(taxService.getAllTax(pageable));
	}

	@GetMapping("/taxType")
	public ResponseEntity<APIResponse> getAllTaxTypes() throws ValidationException {
		return ResponseEntity.ok().body(taxService.getAllTaxTypes());
	}

	@DeleteMapping("/tax/{id}")
	public ResponseEntity<APIResponse> deleteTax(@PathVariable(value = "id") Long id) throws ValidationException {
		return ResponseEntity.ok().body(taxService.deleteTax(id));
	}

	@PutMapping("/tax/{id}")
	public ResponseEntity<APIResponse> updateTax(@PathVariable(value = "id") Long id, @RequestBody TaxRequestDTO dto)
			throws ValidationException {
		return ResponseEntity.ok().body(taxService.updateTax(id, dto));
	}
}
