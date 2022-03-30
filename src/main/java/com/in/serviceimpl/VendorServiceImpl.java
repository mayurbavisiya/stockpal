package com.in.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.entity.VendorDaysScheduleEntity;
import com.in.entity.VendorEntity;
import com.in.exception.ValidationException;
import com.in.repository.VendorDaysScheduleRepository;
import com.in.repository.VendorRepository;
import com.in.request.dto.VendorReqDTO;
import com.in.response.dto.VendorResDTO;
import com.in.service.VendorService;

@Service
public class VendorServiceImpl implements VendorService {

	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	VendorDaysScheduleRepository vendorDaysScheduleRepository;

	@Override
	public APIResponse getVendorContainsName(String vendorName) {
		List<VendorResDTO> vendorList = vendorRepository.getVendorContainsName(vendorName).stream().map(x -> {
			VendorResDTO dto = new VendorResDTO();
			BeanUtils.copyProperties(x, dto);
			dto.setDays(x.getDays().stream().map(y -> String.valueOf(y.getDayId())).collect(Collectors.joining(",")));
			return dto;
		}).collect(Collectors.toList());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setVendors(vendorList);
		return response;
	}

	@Override
	public APIResponse createVendor(VendorReqDTO dto) throws ValidationException {

		List<VendorEntity> vendorList = vendorRepository.getVendorByName(dto.getName().trim());
		if (vendorList.size() > 0)
			throw new ValidationException("dataAlreadyExistCode", "vendorNameAlreadyExist", "successBooleanFalse");

		VendorEntity entity = new VendorEntity();
		BeanUtils.copyProperties(dto, entity);
		entity.setCreatedDate(new Date());
		entity.setIsActive(1);
		vendorRepository.save(entity);

		// Add mapping for schedulers
		if (dto.getDays() != null && !"".equals(dto.getDays())) {
			this.mapSchedulerDaysForVendor(entity, dto.getDays());
		}

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	private void mapSchedulerDaysForVendor(VendorEntity entity, String days) {
		// delete existing mapping
		vendorDaysScheduleRepository.deleteMapping(entity.getId());
		long[] daysArr;

		if (!days.equals("")) {
			daysArr = Stream.of(days.split(",")).mapToLong(Long::parseLong).toArray();
			for (Long day : daysArr) {
				VendorDaysScheduleEntity en = new VendorDaysScheduleEntity();
				en.setCreatedDate(new Date());
				en.setVendorId(entity.getId());
				en.setDayId(day);
				vendorDaysScheduleRepository.save(en);
			}
		}
	}

	@Override
	public APIResponse updateVendor(Long id, VendorReqDTO dto) throws ValidationException {
		VendorEntity vendor = vendorRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "vendorNotFoundDesc", "successBooleanFalse"));

		List<VendorEntity> vendorList = vendorRepository.getVendorByName(dto.getName().trim());
		if (vendorList.size() > 0) {
			if (vendorList.get(0).getId() != id) {
				throw new ValidationException("dataAlreadyExistCode", "vendorNameAlreadyExist", "successBooleanFalse");
			}
		}

		BeanUtils.copyProperties(dto, vendor);
		vendor.setUpdatedDate(new Date());
		vendorRepository.save(vendor);
		// Update mapping for schedulers
		if (dto.getDays() != null) {
			this.mapSchedulerDaysForVendor(vendor, dto.getDays());
		}

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse deleteVendor(Long id) throws ValidationException {
		VendorEntity vendor = vendorRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "vendorNotFoundDesc", "successBooleanFalse"));

		// Delete mapping for vendor day schedulers
//		vendorDaysScheduleRepository.deleteMapping(id);

		// SOFT Delete
		vendor.setIsActive(0);
		vendorRepository.save(vendor);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse getAllVendor(Pageable pageable, String name) {
		Page<VendorEntity> vendorPages;
		vendorPages = vendorRepository.findAll(pageable, name);
		
		List<VendorEntity> enList = vendorPages.getContent();
		Integer totalPages = vendorPages.getTotalPages();
		Integer pageRecords = vendorPages.getNumberOfElements();
		Integer totalRecords = (int) vendorPages.getTotalElements();

		List<VendorResDTO> vendorList = enList.stream().map(x -> {
			VendorResDTO dto = new VendorResDTO();
			BeanUtils.copyProperties(x, dto);
			dto.setDays(x.getDays().stream().map(y -> String.valueOf(y.getDayId())).collect(Collectors.joining(",")));
			return dto;
		}).collect(Collectors.toList());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setVendors(vendorList);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;
	}

	@Override
	public APIResponse getvendorById(Long id) throws ValidationException {

		VendorEntity vendor = vendorRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "vendorNotFoundDesc", "successBooleanFalse"));
		VendorResDTO dto = new VendorResDTO();
		BeanUtils.copyProperties(vendor, dto);
		dto.setDays(vendor.getDays().stream().map(y -> String.valueOf(y.getDayId())).collect(Collectors.joining(",")));
		List<VendorResDTO> vendorList = new ArrayList<VendorResDTO>();
		vendorList.add(dto);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setVendors(vendorList);
		return response;

	}

}
