package com.in.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.repository.DaysLookupRepository;
import com.in.response.dto.DaysLookupDTO;
import com.in.service.LookupService;

@Service
public class LookupServiceImpl implements LookupService {

	@Autowired
	DaysLookupRepository daysLookupRepository;

	@Override
	public APIResponse getAllScheduleDays() {
		List<DaysLookupDTO> daysList = daysLookupRepository.findAll().stream().map(x -> {
			DaysLookupDTO dto = new DaysLookupDTO();
			BeanUtils.copyProperties(x, dto);
			return dto;
		}).collect(Collectors.toList());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setDays(daysList);
		return response;
	}
}
