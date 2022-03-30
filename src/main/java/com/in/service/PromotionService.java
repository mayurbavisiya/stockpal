package com.in.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.request.dto.PromotionReqDTO;

@Service
public interface PromotionService {
	APIResponse savePromotion(PromotionReqDTO dto) throws ValidationException;

	APIResponse getAllPromotions(Pageable pageable, String name);

	APIResponse deletePromotion(Long id) throws ValidationException;

	APIResponse updatePromotion(Long id, PromotionReqDTO dto) throws ValidationException;

	APIResponse getPromotionById(Long id) throws ValidationException;

}
