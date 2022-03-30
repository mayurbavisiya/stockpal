package com.in.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.entity.CategoryEntity;
import com.in.entity.ProductEntity;
import com.in.exception.ValidationException;
import com.in.repository.CategoryRepository;
import com.in.repository.ProductRepository;
import com.in.request.dto.CategoryReqDTO;
import com.in.response.dto.CategoryProductResDTO;
import com.in.response.dto.CategoryResDTO;
import com.in.service.CategoryService;
import com.in.util.Utility;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ProductRepository productRepository;

	@Override
	public APIResponse saveCategory(CategoryReqDTO dto) throws ValidationException {
		if (categoryRepository.getCategoryByName(dto.getName().trim()).size() > 0) {
			throw new ValidationException("dataAlreadyExistCode", "categoryNameExistDesc", "successBooleanFalse");
		}
		CategoryEntity parentCategory = null;
		if (dto.getParentCategoryId() != null) {
			parentCategory = categoryRepository.findById(dto.getParentCategoryId())
					.orElseThrow(() -> new ValidationException("dataNotFoundCode", "ParentCategoryNotFoundDesc",
							"successBooleanFalse"));
		}
		CategoryEntity category = new CategoryEntity();
		category.setDescription(dto.getDescription());
		category.setEbtEnabled(dto.getEbtEnabled() ? 1 : 0);
		category.setMarginPercentage(Double.valueOf(dto.getMarginPercentage()));
		category.setName(dto.getName());
		category.setCreatedDate(new Date());
		category.setIsActive(1);
		if (parentCategory != null) {
			category.setParentCategoryId(parentCategory.getId().intValue());
			category.setParentCategoryName(parentCategory.getName());
		}
		categoryRepository.save(category);

		// Update category id for the products from product table
		updatecategoryForProducts(category.getId(), dto.getProductIds(), dto.getIsAllSelected());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	private void updatecategoryForProducts(Long categoryId, String productIds, Integer isAllSelected) {
		if (isAllSelected != null && isAllSelected == 1) {
			// update for all the available products
			productRepository.updateAllProductsCategory(categoryId.intValue());
		} else if (productIds != null && !productIds.isEmpty()) {
			ArrayList<Integer> productIdsList = (ArrayList<Integer>) Stream.of(productIds.split(","))
					.mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
			productRepository.updateAllProductsCategory(categoryId.intValue(), productIdsList);
		}
	}

	@Override
	public APIResponse getAllCategories(Pageable pageable, String fromDate, String toDate, String name) {
		Page<CategoryEntity> categoryPages = null;
		List<CategoryEntity> enList = null;
		Integer totalPages = 0;
		Integer pageRecords = 0;
		Integer totalRecords = 0;

		fromDate = (fromDate != null && !fromDate.isEmpty()) ? fromDate + " 00:00:00" : null;
		toDate = (toDate != null && !toDate.isEmpty()) ? toDate + " 23:59:59" : null;
		categoryPages = categoryRepository.findAll(pageable, fromDate, toDate, name);
		enList = categoryPages.getContent();
		totalPages = categoryPages.getTotalPages();
		pageRecords = categoryPages.getNumberOfElements();
		totalRecords = (int) categoryPages.getTotalElements();

		List<CategoryResDTO> catList = enList.stream().map(x -> {
			CategoryResDTO dto = new CategoryResDTO();
			BeanUtils.copyProperties(x, dto);
			dto.setParentCategoryName(x.getParentCategoryName());
			dto.setEbtEnabled(x.getEbtEnabled() == 1 ? true : false);
			dto.setMarginPercentage(String.valueOf(x.getMarginPercentage()));
			dto.setCreatedDate(Utility.getStringFromDate(x.getCreatedDate(), true));
			dto.setUpdatedDate(Utility.getStringFromDate(x.getUpdatedDate(), true));
			return dto;
		}).collect(Collectors.toList());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setCategories(catList);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;
	}

	@Override
	public APIResponse updateCategory(Long id, CategoryReqDTO dto) throws ValidationException {
		CategoryEntity category = categoryRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "CategoryNotFoundDesc", "successBooleanFalse"));

		CategoryEntity parentCategory = null;
		if (dto.getParentCategoryId() != null) {
			parentCategory = categoryRepository.findById(dto.getParentCategoryId())
					.orElseThrow(() -> new ValidationException("dataNotFoundCode", "ParentCategoryNotFoundDesc",
							"successBooleanFalse"));
		}

		List<CategoryEntity> catList = categoryRepository.getCategoryByName(dto.getName().trim());
		if (catList.size() > 0) {
			if (catList.get(0).getId() != id) {
				throw new ValidationException("dataAlreadyExistCode", "categoryNameExistDesc", "successBooleanFalse");
			}
		}

		if (dto.getDescription() != null)
			category.setDescription(dto.getDescription());
		if (dto.getEbtEnabled() != null)
			category.setEbtEnabled(dto.getEbtEnabled() ? 1 : 0);
		if (dto.getMarginPercentage() != null)
			category.setMarginPercentage(Double.valueOf(dto.getMarginPercentage()));
		if (dto.getName() != null)
			category.setName(dto.getName());
		if (parentCategory != null) {
			if (dto.getParentCategoryId() != null && parentCategory != null)
				category.setParentCategoryId(dto.getParentCategoryId().intValue());
			if (dto.getParentCategoryId() != null && parentCategory != null)
				category.setParentCategoryName(parentCategory.getName());
		}else{
			category.setParentCategoryId(null);
			category.setParentCategoryName(null);
		}

		category.setUpdatedDate(new Date());
		categoryRepository.save(category);

		// Update category id for the products from product table
		updatecategoryForProducts(category.getId(), dto.getProductIds(), dto.getIsAllSelected());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse deleteCategory(Long id) throws ValidationException {
		CategoryEntity category = categoryRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "CategoryNotFoundDesc", "successBooleanFalse"));
		// Check this categoryId is already mapped to any Product
		List<ProductEntity> productList = productRepository.getAllProductBycategoryId(id.intValue());
		if (productList.size() > 0)
			throw new ValidationException("errorCode", "productExistForcategory", "successBooleanFalse");

		// Not deleting category , Just making isActive 0(SOFT Delete)
		category.setIsActive(0);
		categoryRepository.save(category);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse getCategoryDetailsWithProduct(Long id) throws ValidationException {
		List<Object[]> category = categoryRepository.getCategoryDetailsWithProduct(id.intValue());

		List<CategoryResDTO> responseList = new ArrayList<>();
		List<CategoryProductResDTO> productList = new ArrayList<CategoryProductResDTO>();
		CategoryResDTO dto = new CategoryResDTO();
		for (Object[] obj : category) {
			dto.setId(obj[0] != null ? Long.valueOf(String.valueOf(obj[0])) : 0);
			dto.setName(obj[1] != null ? (String.valueOf(obj[1])) : "");
			dto.setMarginPercentage(obj[2] != null ? (String.valueOf(obj[2])) : "");
			dto.setEbtEnabled(obj[3] != null ? (String.valueOf(obj[3]).equals("1") ? true : false) : false);
			dto.setDescription(obj[4] != null ? (String.valueOf(obj[4])) : "");
			dto.setCreatedDate(obj[5] != null ? (String.valueOf(obj[5])) : "");
			dto.setUpdatedDate(obj[6] != null ? (String.valueOf(obj[6])) : "");
			dto.setParentCategoryId(obj[7] != null ? Integer.valueOf(String.valueOf(obj[7])) : 0);
			dto.setParentCategoryName(obj[8] != null ? (String.valueOf(obj[8])) : "");
			CategoryProductResDTO product = new CategoryProductResDTO();
			product.setId(obj[9] != null ? Long.valueOf(String.valueOf(obj[9])) : 0);
			product.setProductName(obj[10] != null ? (String.valueOf(obj[10])) : "");
			productList.add(product);
		}

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		dto.setProducts(productList);
		if (dto.getId() != null)
			responseList.add(dto);
		response.setCategories(responseList);
		return response;
	}

}
