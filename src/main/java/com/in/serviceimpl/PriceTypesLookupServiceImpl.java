package com.in.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.repository.PriceTypesLookupRepository;
import com.in.response.dto.PriceTypesResDTO;
import com.in.service.PriceTypesLookupService;

@Service
public class PriceTypesLookupServiceImpl implements PriceTypesLookupService {

	@Autowired
	PriceTypesLookupRepository priceTypesLookupRepository;

	@Override
	public APIResponse getPriceTypes() {
		List<PriceTypesResDTO> priceTypesList = priceTypesLookupRepository.findAll().stream().map(x -> {
			PriceTypesResDTO dto = new PriceTypesResDTO();
			BeanUtils.copyProperties(x, dto);
			return dto;
		}).collect(Collectors.toList());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setPriceTypes(priceTypesList); 
		return response;
	}

}
