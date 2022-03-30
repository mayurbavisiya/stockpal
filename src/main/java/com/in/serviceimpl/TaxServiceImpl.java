package com.in.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.entity.TaxEntity;
import com.in.entity.TaxTypesLookupEntity;
import com.in.exception.ValidationException;
import com.in.repository.TaxRepository;
import com.in.repository.TaxTypeLookupRepository;
import com.in.request.dto.TaxRequestDTO;
import com.in.response.dto.TaxResDTO;
import com.in.response.dto.TaxTypesLookupResDTO;
import com.in.service.TaxService;

@Service
public class TaxServiceImpl implements TaxService {

	@Autowired
	TaxRepository taxRepository;

	// @Autowired
	// TaxPaginationRepository taxPaginationRepository;

	@Autowired
	TaxTypeLookupRepository taxTypeLookupRepository;

	@Override
	public APIResponse saveTax(TaxRequestDTO dto) throws ValidationException {

		if (taxRepository.getTaxByName(dto.getRateName().trim()).size() > 0) {
			throw new ValidationException("dataAlreadyExistCode", "rateNameAlreadyExistDesc", "successBooleanFalse");
		}

		TaxTypesLookupEntity taxType = taxTypeLookupRepository.findById(dto.getTaxType());
		if (taxType == null)
			throw new ValidationException("dataNotFoundCode", "taxNotFoundDesc", "successBooleanFalse");

		TaxEntity en = new TaxEntity();
		en.setRateName(dto.getRateName());
		en.setTaxType(taxType);
		en.setAmount(Double.valueOf(dto.getAmount()));
		en.setCreatedDate(new Date());
		en.setFlag(1);
		en.setIsDefault(dto.getIsDefault() != null ? dto.getIsDefault() : 0);
		taxRepository.save(en);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;

	}

	@Override
	public APIResponse getAllTax(Pageable pageable) {
		Page<TaxEntity> taxPages = taxRepository.findAll(pageable);
		List<TaxEntity> enList = taxPages.getContent();
		Integer totalPages = taxPages.getTotalPages();
		Integer pageRecords = taxPages.getNumberOfElements();
		Integer totalRecords = (int) taxPages.getTotalElements();

		List<TaxResDTO> taxList = enList.stream().map(x -> {
			TaxResDTO dto = new TaxResDTO();
			BeanUtils.copyProperties(x, dto);
			dto.setTaxTypeId(x.getTaxType().getId().intValue());
			dto.setTaxTypeName(x.getTaxType().getTypeDesc());
			dto.setAmount(String.valueOf(x.getAmount()));
			return dto;
		}).collect(Collectors.toList());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setTax(taxList);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;
	}

	@Override
	public APIResponse deleteTax(Long id) throws ValidationException {

		// TaxEntity tax = taxRepository.findById(id).orElseThrow(
		// () -> new ValidationException("dataNotFoundCode", "taxNotFoundDesc",
		// "successBooleanFalse"));
		// taxRepository.delete(tax);
		//
		// APIResponse response = new APIResponse("successCode", "successMsg",
		// "successBooleanTrue");
		// return response;

		TaxEntity tax = taxRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "taxNotFoundDesc", "successBooleanFalse"));

		tax.setFlag(0);
		taxRepository.save(tax);

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse updateTax(Long id, TaxRequestDTO dto) throws ValidationException {
		TaxEntity tax = taxRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "taxNotFoundDesc", "successBooleanFalse"));

		List<TaxEntity> taxList = taxRepository.getTaxByName(dto.getRateName().trim());
		if (taxList.size() > 0) {
			if (taxList.get(0).getId() != id) {
				throw new ValidationException("dataAlreadyExistCode", "rateNameAlreadyExistDesc",
						"successBooleanFalse");
			}
		}

		if (dto.getAmount() != null) {
			if (!"".equals(dto.getAmount())) {
				tax.setAmount(Double.valueOf(dto.getAmount()));
			}
		}

		if (dto.getRateName() != null) {
			if (!"".equals(dto.getRateName())) {
				tax.setRateName(dto.getRateName());
			}
		}

		if (dto.getIsDefault() != null) {
			tax.setIsDefault(dto.getIsDefault());
		}

		tax.setUpdatedDate(new Date());
		taxRepository.save(tax);

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse getAllTaxTypes() {
		List<TaxTypesLookupResDTO> taxLookupList = taxTypeLookupRepository.findAll().stream().map(x -> {
			TaxTypesLookupResDTO dto = new TaxTypesLookupResDTO();
			BeanUtils.copyProperties(x, dto);
			return dto;
		}).collect(Collectors.toList());

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setTaxTypes(taxLookupList);
		return response;
	}

}
