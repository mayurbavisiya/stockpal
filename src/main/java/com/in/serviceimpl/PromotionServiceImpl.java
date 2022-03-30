package com.in.serviceimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.entity.PromotionEntity;
import com.in.entity.PromotionTypeDaysEntity;
import com.in.entity.PromotionTypeEntity;
import com.in.entity.PromotionTypeProductMapEntity;
import com.in.entity.PromotionTypeTimePeriodEntity;
import com.in.exception.ValidationException;
import com.in.repository.ProductRepository;
import com.in.repository.PromotionRepository;
import com.in.request.dto.PromotionReqDTO;
import com.in.request.dto.PromotionTimePeriodReqDTO;
import com.in.response.dto.PromotionResDTO;
import com.in.response.dto.PromotionTypeDaysResDTO;
import com.in.response.dto.PromotionTypeProductMapResDTO;
import com.in.response.dto.PromotionTypeResDTO;
import com.in.response.dto.PromotionTypeTimePeriodResDTO;
import com.in.service.PromotionService;
import com.in.util.Utility;

@Service
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	PromotionRepository promotionRepository;

	@Autowired
	ProductRepository productRepository;

	@Override
	public APIResponse savePromotion(PromotionReqDTO dto) throws ValidationException {

		List<PromotionEntity> promotionList = promotionRepository.getPromotionByName(dto.getName().trim());
		if (promotionList.size() > 0)
			throw new ValidationException("dataAlreadyExistCode", "PromotionAlreadyExist", "successBooleanFalse");

		PromotionEntity promotion = new PromotionEntity();
		BeanUtils.copyProperties(dto, promotion);
		if (dto.getStartDate() != null)
			promotion.setStartDate(Utility.getDateFromString(dto.getStartDate()));
		if (dto.getEndDate() != null)
			promotion.setEndDate(Utility.getDateFromString(dto.getEndDate()));

		Set<PromotionTypeEntity> promotionTypeSet = new HashSet<PromotionTypeEntity>();
		PromotionTypeEntity promotionTypeEn = new PromotionTypeEntity();
		promotionTypeEn.setApplyId(dto.getPromotionType().getApplyId());
		if (dto.getPromotionType().getBuy() != null)
			promotionTypeEn.setBuy(dto.getPromotionType().getBuy());
		if (dto.getPromotionType().getProductId() != null)
			promotionTypeEn.setProductId(dto.getPromotionType().getProductId());
		if (dto.getPromotionType().getGetId() != null)
			promotionTypeEn.setGetId(dto.getPromotionType().getGetId());
		if (dto.getPromotionType().getGetAmount() != null)
			promotionTypeEn.setGetAmount(dto.getPromotionType().getGetAmount());
		if (dto.getPromotionType().getOf() != null)
			promotionTypeEn.setOf(dto.getPromotionType().getOf());
		if (dto.getPromotionType().getLimitTo() != null)
			promotionTypeEn.setLimitTo(dto.getPromotionType().getLimitTo());
		if (dto.getPromotionType().getDiscountOnEvery() != null)
			promotionTypeEn.setDiscountOnEvery(dto.getPromotionType().getDiscountOnEvery());
		if (dto.getPromotionType().getBuyingUnit() != null)
			promotionTypeEn.setBuyingUnit(dto.getPromotionType().getBuyingUnit());
		if (dto.getPromotionType().getDiscountAmount() != null)
			promotionTypeEn.setDiscountAmount(dto.getPromotionType().getDiscountAmount());
		promotionTypeSet.add(promotionTypeEn);
		promotion.setPromotionType(promotionTypeSet);

		if (dto.getPromotionTimePeriods() != null) {
			Set<PromotionTypeTimePeriodEntity> promotionTypeTimePeriodSet = new HashSet<>();
			for (PromotionTimePeriodReqDTO timePeriodDTo : dto.getPromotionTimePeriods()) {
				PromotionTypeTimePeriodEntity promotionTypeTimePeriodEntity = new PromotionTypeTimePeriodEntity();
				if (timePeriodDTo.getFrom() != null)
					promotionTypeTimePeriodEntity.setFrom(timePeriodDTo.getFrom());
				if (timePeriodDTo.getTo() != null)
					promotionTypeTimePeriodEntity.setTo(timePeriodDTo.getTo());

				Set<PromotionTypeDaysEntity> promotionTypeDaysSet = new HashSet<>();
				int[] dayIdArr = Stream.of(timePeriodDTo.getDays().split(",")).mapToInt(Integer::parseInt).toArray();
				for (int i = 0; i < dayIdArr.length; i++) {
					PromotionTypeDaysEntity dayEn = new PromotionTypeDaysEntity();
					dayEn.setDayId(dayIdArr[i]);
					dayEn.setPromotion(promotion);
					promotionTypeDaysSet.add(dayEn);
				}
				promotionTypeTimePeriodEntity.setPromotionTypeDays(promotionTypeDaysSet);
				promotionTypeTimePeriodSet.add(promotionTypeTimePeriodEntity);
			}
			promotion.setPromotionTypeTimePeriod(promotionTypeTimePeriodSet);
		}

		if (dto.getPromotionProduct() != null && dto.getPromotionProduct().getProductIds() != null
				&& !"".equals(dto.getPromotionProduct().getProductIds())) {
			Set<PromotionTypeProductMapEntity> promotionTypeProductSet = new HashSet<>();
			int[] productArr = Stream.of(dto.getPromotionProduct().getProductIds().split(","))
					.mapToInt(Integer::parseInt).toArray();
			for (int productId : productArr) {
				PromotionTypeProductMapEntity productMapEn = new PromotionTypeProductMapEntity();
				productMapEn.setProductId(productId);
				promotionTypeProductSet.add(productMapEn);
			}
			promotion.setPromotionTypeProductMap(promotionTypeProductSet);
		}

		promotionRepository.save(promotion);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse getAllPromotions(Pageable pageable, String name) {

		Page<PromotionEntity> promotionPages;
		name = (name != null && !name.isEmpty()) ? name.toLowerCase() : name;
		promotionPages = promotionRepository.findAll(pageable, name);
		List<PromotionEntity> enList = promotionPages.getContent();
		Integer totalPages = promotionPages.getTotalPages();
		Integer pageRecords = promotionPages.getNumberOfElements();
		Integer totalRecords = (int) promotionPages.getTotalElements();

		List<PromotionResDTO> promotionList = enList.stream().map(x -> {
			PromotionResDTO dto = new PromotionResDTO();
			BeanUtils.copyProperties(x, dto);
			dto.setStartDate(Utility.getStringFromDate(x.getStartDate(), false));
			dto.setEndDate(Utility.getStringFromDate(x.getEndDate(), false));

			PromotionTypeResDTO promotionType = new PromotionTypeResDTO();
			for (PromotionTypeEntity typeEn : x.getPromotionType()) {
				BeanUtils.copyProperties(typeEn, promotionType);
			}
			dto.setPromotionType(promotionType);

			List<PromotionTypeTimePeriodResDTO> timePeriodList = new ArrayList<>();
			for (PromotionTypeTimePeriodEntity timeEn : x.getPromotionTypeTimePeriod()) {
				PromotionTypeTimePeriodResDTO timePeriodDTO = new PromotionTypeTimePeriodResDTO();
				BeanUtils.copyProperties(timeEn, timePeriodDTO);
				List<PromotionTypeDaysResDTO> daysList = new ArrayList<>();
				for (PromotionTypeDaysEntity daysEn : timeEn.getPromotionTypeDays()) {
					PromotionTypeDaysResDTO dayDto = new PromotionTypeDaysResDTO();
					BeanUtils.copyProperties(daysEn, dayDto);
					daysList.add(dayDto);
				}
				timePeriodDTO.setPromotionTypeDays(daysList);
				timePeriodList.add(timePeriodDTO);
			}
			dto.setPromotionTimePeriod(timePeriodList);

			List<PromotionTypeProductMapResDTO> prmotionMappedProductsList = new ArrayList<>();
			for (PromotionTypeProductMapEntity productEn : x.getPromotionTypeProductMap()) {
				PromotionTypeProductMapResDTO productDTO = new PromotionTypeProductMapResDTO();
				BeanUtils.copyProperties(productEn, productDTO);
				prmotionMappedProductsList.add(productDTO);
			}
			dto.setPrmotionMappedProducts(prmotionMappedProductsList);

			return dto;
		}).collect(Collectors.toList());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		if (promotionList == null || promotionList.isEmpty())
			response.setCommonMessage("We don't have any promotion to show here.");
		response.setPromotions(promotionList);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;

	}

	@Override
	public APIResponse deletePromotion(Long id) throws ValidationException {
		PromotionEntity promotion = promotionRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "PromotionNotFoundDesc", "successBooleanFalse"));

		// Update promotion entry in product
		updatePromotionInProducts(id);
		promotionRepository.delete(promotion);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	private void updatePromotionInProducts(Long promotionId) {
		productRepository.updatePromotionInProducts(promotionId.intValue());
	}

	@Override
	public APIResponse updatePromotion(Long id, PromotionReqDTO dto) throws ValidationException {
		PromotionEntity promotion = promotionRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "PromotionNotFoundDesc", "successBooleanFalse"));

		if (dto.getStartDate() != null)
			promotion.setStartDate(Utility.getDateFromString(dto.getStartDate()));
		if (dto.getEndDate() != null)
			promotion.setEndDate(Utility.getDateFromString(dto.getEndDate()));
		if (dto.getName() != null)
			promotion.setName(dto.getName());
		if (dto.getPromotionDesc() != null)
			promotion.setPromotionDesc(dto.getPromotionDesc());
		if (dto.getStatusId() != null)
			promotion.setStatusId(dto.getStatusId());
		if (dto.getDurationId() != null)
			promotion.setDurationId(dto.getDurationId());

		if (dto.getPromotionType() != null) {
			Set<PromotionTypeEntity> promotionTypeSet = new HashSet<PromotionTypeEntity>();
			PromotionTypeEntity promotionTypeEn = new PromotionTypeEntity();
			if (dto.getPromotionType().getApplyId() != null)
				promotionTypeEn.setApplyId(dto.getPromotionType().getApplyId());
			if (dto.getPromotionType().getBuy() != null)
				promotionTypeEn.setBuy(dto.getPromotionType().getBuy());
			if (dto.getPromotionType().getProductId() != null)
				promotionTypeEn.setProductId(dto.getPromotionType().getProductId());
			if (dto.getPromotionType().getGetId() != null)
				promotionTypeEn.setGetId(dto.getPromotionType().getGetId());
			if (dto.getPromotionType().getGetAmount() != null)
				promotionTypeEn.setGetAmount(dto.getPromotionType().getGetAmount());
			if (dto.getPromotionType().getOf() != null)
				promotionTypeEn.setOf(dto.getPromotionType().getOf());
			if (dto.getPromotionType().getLimitTo() != null)
				promotionTypeEn.setLimitTo(dto.getPromotionType().getLimitTo());
			if (dto.getPromotionType().getDiscountOnEvery() != null)
				promotionTypeEn.setDiscountOnEvery(dto.getPromotionType().getDiscountOnEvery());
			if (dto.getPromotionType().getBuyingUnit() != null)
				promotionTypeEn.setBuyingUnit(dto.getPromotionType().getBuyingUnit());
			if (dto.getPromotionType().getDiscountAmount() != null)
				promotionTypeEn.setDiscountAmount(dto.getPromotionType().getDiscountAmount());
			promotionTypeSet.add(promotionTypeEn);
			if (promotionTypeSet != null && promotionTypeSet.size() > 0) {
				// Delete existing promotion type
				promotionRepository.deletePromotionTypeByPromotionId(promotion.getId().intValue());
			}
			promotion.setPromotionType(promotionTypeSet);
		}

		if (dto.getPromotionTimePeriods() != null) {

			Set<PromotionTypeTimePeriodEntity> promotionTypeTimePeriodSet = new HashSet<>();
			for (PromotionTimePeriodReqDTO timePeriodDTo : dto.getPromotionTimePeriods()) {
				PromotionTypeTimePeriodEntity promotionTypeTimePeriodEntity = new PromotionTypeTimePeriodEntity();
				promotionTypeTimePeriodEntity.setFrom(timePeriodDTo.getFrom());
				promotionTypeTimePeriodEntity.setTo(timePeriodDTo.getTo());

				Set<PromotionTypeDaysEntity> promotionTypeDaysSet = new HashSet<>();
				int[] dayIdArr = Stream.of(timePeriodDTo.getDays().split(",")).mapToInt(Integer::parseInt).toArray();
				for (int i = 0; i < dayIdArr.length; i++) {
					PromotionTypeDaysEntity dayEn = new PromotionTypeDaysEntity();
					dayEn.setDayId(dayIdArr[i]);
					dayEn.setPromotion(promotion);
					promotionTypeDaysSet.add(dayEn);
				}
				promotionTypeTimePeriodEntity.setPromotionTypeDays(promotionTypeDaysSet);
				promotionTypeTimePeriodSet.add(promotionTypeTimePeriodEntity);
			}
			if (promotionTypeTimePeriodSet != null) {
				// Delete existing promotion time periods data
				promotionRepository.deletePromotionTypeTimePeriodByPromotionId(promotion.getId().intValue());
			}

			promotion.setPromotionTypeTimePeriod(promotionTypeTimePeriodSet);
		}

		if (dto.getPromotionProduct() != null && dto.getPromotionProduct().getProductIds() != null
				&& !"".equals(dto.getPromotionProduct().getProductIds())) {

			Set<PromotionTypeProductMapEntity> promotionTypeProductSet = new HashSet<>();
			int[] productArr = Stream.of(dto.getPromotionProduct().getProductIds().split(","))
					.mapToInt(Integer::parseInt).toArray();
			for (int productId : productArr) {
				PromotionTypeProductMapEntity productMapEn = new PromotionTypeProductMapEntity();
				productMapEn.setProductId(productId);
				promotionTypeProductSet.add(productMapEn);
			}
			if (promotionTypeProductSet != null && promotionTypeProductSet.size() > 0) {
				// Delete existing product Mapping
				promotionRepository.deletePromotionTypeProductMapByPromotionId(promotion.getId().intValue());
			}
			promotion.setPromotionTypeProductMap(promotionTypeProductSet);
		}

		promotionRepository.save(promotion);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse getPromotionById(Long id) throws ValidationException {
		PromotionEntity x = promotionRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "PromotionNotFoundDesc", "successBooleanFalse"));

		PromotionResDTO dto = new PromotionResDTO();
		BeanUtils.copyProperties(x, dto);
		dto.setStartDate(Utility.getStringFromDate(x.getStartDate(), false));
		dto.setEndDate(Utility.getStringFromDate(x.getEndDate(), false));

		PromotionTypeResDTO promotionType = new PromotionTypeResDTO();
		for (PromotionTypeEntity typeEn : x.getPromotionType()) {
			BeanUtils.copyProperties(typeEn, promotionType);
		}
		dto.setPromotionType(promotionType);

		List<PromotionTypeTimePeriodResDTO> timePeriodList = new ArrayList<>();
		for (PromotionTypeTimePeriodEntity timeEn : x.getPromotionTypeTimePeriod()) {
			PromotionTypeTimePeriodResDTO timePeriodDTO = new PromotionTypeTimePeriodResDTO();
			BeanUtils.copyProperties(timeEn, timePeriodDTO);
			List<PromotionTypeDaysResDTO> daysList = new ArrayList<>();
			for (PromotionTypeDaysEntity daysEn : timeEn.getPromotionTypeDays()) {
				PromotionTypeDaysResDTO dayDto = new PromotionTypeDaysResDTO();
				BeanUtils.copyProperties(daysEn, dayDto);
				daysList.add(dayDto);
			}
			timePeriodDTO.setPromotionTypeDays(daysList);
			timePeriodList.add(timePeriodDTO);
		}
		dto.setPromotionTimePeriod(timePeriodList);

		List<PromotionTypeProductMapResDTO> prmotionMappedProductsList = new ArrayList<>();
		for (PromotionTypeProductMapEntity productEn : x.getPromotionTypeProductMap()) {
			PromotionTypeProductMapResDTO productDTO = new PromotionTypeProductMapResDTO();
			BeanUtils.copyProperties(productEn, productDTO);
			prmotionMappedProductsList.add(productDTO);
		}
		dto.setPrmotionMappedProducts(prmotionMappedProductsList);

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setPromotion(dto);
		return response;

	}

}
