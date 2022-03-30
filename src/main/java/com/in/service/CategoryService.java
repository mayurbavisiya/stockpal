package com.in.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.request.dto.CategoryReqDTO;

@Service
public interface CategoryService {

	APIResponse saveCategory(CategoryReqDTO dto) throws ValidationException;

	APIResponse getAllCategories(Pageable pageable, String fromDate, String toDate, String name);

	APIResponse updateCategory(Long id, CategoryReqDTO dto) throws ValidationException;

	APIResponse deleteCategory(Long id) throws ValidationException;

	APIResponse getCategoryDetailsWithProduct(Long id) throws ValidationException;

}
