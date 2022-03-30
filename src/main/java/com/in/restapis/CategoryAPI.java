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
import com.in.request.dto.CategoryReqDTO;
import com.in.service.CategoryService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CategoryAPI {

	@Autowired
	CategoryService categoryService;

	@PostMapping("/category")
	public ResponseEntity<APIResponse> saveCategory(@RequestBody CategoryReqDTO dto) throws ValidationException {
		return ResponseEntity.ok().body(categoryService.saveCategory(dto));
	}

	@GetMapping("/category")
	public ResponseEntity<APIResponse> getAllCategories(Pageable pageable,
			@RequestParam(required = false, value = "fromDate") String fromDate,
			@RequestParam(required = false, value = "toDate") String toDate,
			@RequestParam(required = false, value = "name") String name) throws ValidationException {
		return ResponseEntity.ok().body(categoryService.getAllCategories(pageable, fromDate, toDate, name));
	}

	@PutMapping("/category/{id}")
	public ResponseEntity<APIResponse> updateCategory(@PathVariable(value = "id") Long id,
			@RequestBody CategoryReqDTO dto) throws ValidationException {
		return ResponseEntity.ok().body(categoryService.updateCategory(id, dto));
	}

	@DeleteMapping("/category/{id}")
	public ResponseEntity<APIResponse> deleteCategory(@PathVariable(value = "id") Long id) throws ValidationException {
		return ResponseEntity.ok().body(categoryService.deleteCategory(id));
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<APIResponse> getCategoryDetailsWithProduct(@PathVariable(value = "id") Long id)
			throws ValidationException {
		return ResponseEntity.ok().body(categoryService.getCategoryDetailsWithProduct(id));
	}

}
