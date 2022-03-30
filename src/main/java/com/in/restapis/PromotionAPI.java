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
import com.in.request.dto.PromotionReqDTO;
import com.in.service.PromotionService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PromotionAPI {

	@Autowired
	PromotionService promotionService;

	@PostMapping("/promotion")
	public ResponseEntity<APIResponse> savePromotion(@RequestBody PromotionReqDTO dto) throws ValidationException {
		return ResponseEntity.ok().body(promotionService.savePromotion(dto));
	}

	@GetMapping("/promotions")
	public ResponseEntity<APIResponse> getAllPromotions(Pageable pageable, @RequestParam(required = false) String name)
			throws ValidationException {
		return ResponseEntity.ok().body(promotionService.getAllPromotions(pageable, name));
	}

	@GetMapping("/promotion/{id}")
	public ResponseEntity<APIResponse> getPromotionById(@PathVariable(value = "id") Long id)
			throws ValidationException {
		return ResponseEntity.ok().body(promotionService.getPromotionById(id));
	}

	@PutMapping("/promotion/{id}")
	public ResponseEntity<APIResponse> updatePromotion(@PathVariable(value = "id") Long id,
			@RequestBody PromotionReqDTO dto) throws ValidationException {
		return ResponseEntity.ok().body(promotionService.updatePromotion(id, dto));
	}

	@DeleteMapping("/promotion/{id}")
	public ResponseEntity<APIResponse> deletePromotion(@PathVariable(value = "id") Long id) throws ValidationException {
		return ResponseEntity.ok().body(promotionService.deletePromotion(id));
	}

}
