package com.in.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.repository.SizeLookupRepository;
import com.in.response.dto.SizeLookupResDTO;
import com.in.service.SizeLookupService;

@Service
public class SizeLookupServiceImpl implements SizeLookupService {

	@Autowired
	SizeLookupRepository sizeLookupRepository;

	@Override
	public APIResponse getAllSize() {
		List<SizeLookupResDTO> sizeList = sizeLookupRepository.findAll().stream().map(x -> {
			SizeLookupResDTO dto = new SizeLookupResDTO();
			BeanUtils.copyProperties(x, dto);
			return dto;
		}).collect(Collectors.toList());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setSizes(sizeList);
		return response;
	}

}
