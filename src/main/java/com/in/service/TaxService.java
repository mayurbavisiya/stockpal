package com.in.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.request.dto.TaxRequestDTO;

@Service
@Transactional
public interface TaxService {

	APIResponse saveTax(TaxRequestDTO dto) throws ValidationException;

	APIResponse getAllTax(Pageable pageable);

	APIResponse deleteTax(Long id) throws ValidationException;

	APIResponse updateTax(Long id, TaxRequestDTO dto) throws ValidationException;

	APIResponse getAllTaxTypes();

}
