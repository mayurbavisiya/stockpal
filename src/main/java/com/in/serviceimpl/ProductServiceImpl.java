package com.in.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.in.amazon.AmazonClient;
import com.in.bean.APIResponse;
import com.in.entity.CategoryEntity;
import com.in.entity.ProductEntity;
import com.in.entity.ProductOtherDetailsEntity;
import com.in.entity.ProductPackEntity;
import com.in.entity.ProductPriceEntity;
import com.in.entity.ProductStockAdjustementEntity;
import com.in.entity.ProductStockEntity;
import com.in.entity.ProductTagsEntity;
import com.in.entity.VendorEntity;
import com.in.exception.ValidationException;
import com.in.repository.CategoryRepository;
import com.in.repository.ProductPackRepository;
import com.in.repository.ProductRepository;
import com.in.repository.ProductStockAdjustmentRepository;
import com.in.repository.ProductStockRepository;
import com.in.repository.VendorRepository;
import com.in.request.dto.ProductPackReqDTO;
import com.in.request.dto.ProductReqDTO;
import com.in.request.dto.StockAdjustmentReqDTO;
import com.in.response.dto.ProductOtherDetailsResDTO;
import com.in.response.dto.ProductPackResDTO;
import com.in.response.dto.ProductPricingResDTO;
import com.in.response.dto.ProductResDTO;
import com.in.response.dto.ProductStockAdjustmentResDTO;
import com.in.response.dto.ProductStockResDTO;
import com.in.response.dto.ProductTagsResDTO;
import com.in.service.ProductService;
import com.in.util.Utility;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	private AmazonClient amazonClient;

	@Autowired
	ProductPackRepository productPackRepository;

	@Autowired
	ProductStockRepository productStockRepository;

	@Autowired
	ProductStockAdjustmentRepository productStockAdjustmentRepository;

	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	PushNotificationServiceImpl pushNotification;

	@Override
	public APIResponse saveProduct(ProductReqDTO dto) throws ValidationException {
		// Validation dto request

		List<ProductEntity> products = productRepository.getProductByName(dto.getName().trim());
		if (products.size() > 0)
			throw new ValidationException("dataAlreadyExistCode", "ProductAlreadyExist", "successBooleanFalse");

		// Check BarCode and SKU is present or not for product as well product
		// pack
		checkBarodeAndSKUIsUniqueForProduct(dto, -1L);
		checkBarodeAndSKUIsUniqueForPack(dto);

		ProductEntity product = new ProductEntity();
		product.setPosFlag(dto.getShowPOS() ? 1 : 0);
		product.setName(dto.getName());
		product.setImageURL(dto.getProductImageURL());
		product.setCategory(dto.getCategoryId());
		product.setCreatedDate(new Date());
		product.setIsActive(1);

		if (dto.getBarCode() != null)
			product.setBarCode(dto.getBarCode());
		if (dto.getSku() != null)
			product.setSKU(dto.getSku());
		if (dto.getProductDesc() != null)
			product.setDesc(dto.getProductDesc());
		if (dto.getSizeId() != null)
			product.setSize(dto.getSizeId());
		if (dto.getVolume() != null)
			product.setVolume(dto.getVolume());

		if (dto.getTags() != null && !dto.getTags().isEmpty()) {

			Set<ProductTagsEntity> tagsEntitySet = new HashSet<ProductTagsEntity>();
			String[] tagsArr = dto.getTags().split(",");
			for (String tag : tagsArr) {
				ProductTagsEntity tagEn = new ProductTagsEntity();
				tagEn.setDesc(tag);
				tagsEntitySet.add(tagEn);
			}
			product.setProductTags(tagsEntitySet);
		}

		Set<ProductPriceEntity> priceEntitySet = new HashSet<ProductPriceEntity>();
		ProductPriceEntity priceEn = new ProductPriceEntity();
		priceEn.setCostPrice(dto.getProductPricing().getCostPrice());
		priceEn.setMargin(dto.getProductPricing().getMargin());
		priceEn.setPriceType(dto.getProductPricing().getPriceTypeId());
		if (dto.getProductPricing().getSellingPrice() != null)
			priceEn.setSellingPrice(dto.getProductPricing().getSellingPrice());
		if (dto.getProductPricing().getUnit() != null)
			priceEn.setUnit(dto.getProductPricing().getUnit());
		priceEn.setTax(dto.getProductPricing().getTaxId());
		priceEn.setVendorName(dto.getProductPricing().getVendorNameId());
		priceEntitySet.add(priceEn);
		product.setProductPriceEntity(priceEntitySet);

		Set<ProductStockEntity> stockEntitySet = new HashSet<ProductStockEntity>();
		ProductStockEntity stockEn = new ProductStockEntity();
		stockEn.setQuantity(dto.getProductStockDetails().getStockQty());
		stockEn.setReorderQuantity(dto.getProductStockDetails().getReorderQty());
		stockEn.setParLevel(dto.getProductStockDetails().getParLevel());
		stockEntitySet.add(stockEn);
		product.setProductStockEntity(stockEntitySet);

		Set<ProductOtherDetailsEntity> otherDtlsEntitySet = new HashSet<ProductOtherDetailsEntity>();
		ProductOtherDetailsEntity dtlsEn = new ProductOtherDetailsEntity();
		dtlsEn.setQuantityDetails(dto.getProductOtherDetails().getQtyDetails());

		if (dto.getProductOtherDetails().getPromotionId() != null && dto.getProductOtherDetails().getPromotionId() > 0)
			dtlsEn.setPromotions(dto.getProductOtherDetails().getPromotionId());

		if (dto.getProductOtherDetails() != null && dto.getProductOtherDetails().getExpiryDate() != null)
			dtlsEn.setPromotionExpiryDate(Utility.getDateFromString(dto.getProductOtherDetails().getExpiryDate()));
		if (dto.getProductOtherDetails() != null && dto.getProductOtherDetails().getAlertBeforeDays() != null)
			dtlsEn.setAlertBeforeDays(dto.getProductOtherDetails().getAlertBeforeDays());
		dtlsEn.setEbtEnabled(dto.getProductOtherDetails().getEbtEnabled());
		otherDtlsEntitySet.add(dtlsEn);
		product.setProductOtherDetailsEntity(otherDtlsEntitySet);

		if (dto.getProductOtherDetails() != null && dto.getProductOtherDetails().getProductPacks() != null) {
			List<ProductPackReqDTO> productPacksReq = dto.getProductOtherDetails().getProductPacks();
			Set<ProductPackEntity> productPacks = new HashSet<ProductPackEntity>();
			for (ProductPackReqDTO packDTO : productPacksReq) {
				ProductPackEntity packEn = new ProductPackEntity();
				packEn.setName(packDTO.getPackName());
				packEn.setTotalPacksPerCase(packDTO.getTotalPacksPerCase());
				packEn.setSinglePerCase(packDTO.getSinglePerCase());
				packEn.setCaseCost(packDTO.getCaseCost());
				packEn.setCasePrice(packDTO.getCasePrice());
				packEn.setCaseParLevel(packDTO.getCaseParLevel());
				packEn.setCaseReOrderQty(packDTO.getCaseReorderQty());
				packEn.setSku(packDTO.getSku());
				packEn.setBarCode(packDTO.getBarCode());
				productPacks.add(packEn);
			}
			product.setProductPackEntity(productPacks);
		}

		productRepository.save(product);

		// Save Date into Notification
		productRepository.insertProductAddedNotification(
				Integer.parseInt(String.valueOf(Utility.getFCMProperty("new.product.add.noti.type"))),
				String.valueOf(Utility.getFCMProperty("new.product.add.noti.title")), product.getName());

		// Push Notification
		pushNotification.pushNotification(
				Integer.parseInt(String.valueOf(Utility.getFCMProperty("new.product.add.noti.type"))),
				String.valueOf(Utility.getFCMProperty("new.product.add.noti.title")), product.getName());

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	private void checkBarodeAndSKUIsUniqueForProduct(ProductReqDTO dto, Long id) throws ValidationException {
		if (dto.getBarCode() == null || dto.getBarCode().isEmpty()) {
			throw new ValidationException("dataAlreadyExistCode", "BarcodeCantBeEmpty", "successBooleanFalse");
		}

		if (dto.getSku() == null || dto.getSku().isEmpty()) {
			throw new ValidationException("dataAlreadyExistCode", "SKUCantBeEmpty", "successBooleanFalse");
		}

		if (dto.getBarCode() != null && !dto.getBarCode().isEmpty()) {
			List<ProductEntity> productList = productRepository.getProductByBarCodeId(dto.getBarCode());
			if (productList != null && productList.size() > 0) {
				if (productList.get(0).getId().intValue() != id.intValue()) {
					throw new ValidationException("dataAlreadyExistCode", "BarcodeAlreadyExist", "successBooleanFalse");
				}
			}
		}

		if (dto.getSku() != null && !dto.getSku().isEmpty()) {
			List<ProductEntity> productList = productRepository.getProductBySKU(dto.getSku());
			if (productList != null && productList.size() > 0) {
				if (productList.get(0).getId().intValue() != id.intValue()) {
					throw new ValidationException("dataAlreadyExistCode", "SKUAlreadyExist", "successBooleanFalse");
				}
			}
		}
	}

	private void checkBarodeAndSKUIsUniqueForPack(ProductReqDTO dto) throws ValidationException {

		if (dto.getProductOtherDetails() != null && dto.getProductOtherDetails().getProductPacks() != null) {
			Set<String> barCodeSet = new HashSet<>();
			for (ProductPackReqDTO pack : dto.getProductOtherDetails().getProductPacks()) {
				if (pack.getBarCode() == null || pack.getBarCode().isEmpty()) {
					throw new ValidationException("dataAlreadyExistCode", "BarcodeCantBeEmptyForPack",
							"successBooleanFalse");
				}
				barCodeSet.add(pack.getBarCode().trim());

				List<ProductPackEntity> productList = productPackRepository
						.getProductPackByBarCodeId(pack.getBarCode());
				if (productList != null && productList.size() > 0) {
					throw new ValidationException("dataAlreadyExistCode", "BarcodeAlreadyExistPack",
							"successBooleanFalse");
				}
			}
			if (barCodeSet.size() != dto.getProductOtherDetails().getProductPacks().size()) {
				throw new ValidationException("dataAlreadyExistCode", "BarcodeAlreadyExistInRequest",
						"successBooleanFalse");
			}
		}

		if (dto.getProductOtherDetails() != null && dto.getProductOtherDetails().getProductPacks() != null) {
			Set<String> SKUSet = new HashSet<>();
			for (ProductPackReqDTO pack : dto.getProductOtherDetails().getProductPacks()) {
				if (dto.getSku() == null || dto.getSku().isEmpty()) {
					throw new ValidationException("dataAlreadyExistCode", "SKUCantBeEmptyForPack",
							"successBooleanFalse");
				}
				SKUSet.add(pack.getBarCode().trim());
				List<ProductPackEntity> productList = productPackRepository.getProductPackBySKU(pack.getSku());
				if (productList != null && productList.size() > 0) {
					throw new ValidationException("dataAlreadyExistCode", "SKUAlreadyExistPack", "successBooleanFalse");
				}
			}
			if (SKUSet.size() != dto.getProductOtherDetails().getProductPacks().size()) {
				throw new ValidationException("dataAlreadyExistCode", "SKUAlreadyExistInRequest",
						"successBooleanFalse");
			}
		}

	}

	@Override
	public APIResponse deleteProduct(Long id) throws ValidationException {
		ProductEntity product = productRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "ProductNotFoundDesc", "successBooleanFalse"));

		// SOFT delete
		product.setIsActive(0);
		productRepository.save(product);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse getAllProducts(Pageable pageable, String name, String fromDate, String toDate,
			Integer categoryId, String inventoryType) throws ValidationException {
		Integer totalPages = 0;
		Integer pageRecords = 0;
		Integer totalRecords = 0;
		List<ProductEntity> enList = null;

		fromDate = (fromDate != null && !fromDate.isEmpty()) ? fromDate + " 00:00:00" : null;
		toDate = (toDate != null && !toDate.isEmpty()) ? toDate + " 23:59:59" : null;

		String activeInventory = null, lowInventory = null, outOfInventory = null, disContinue = null;
		if (inventoryType != null && !"".equals(inventoryType)) {
			if (inventoryType.equalsIgnoreCase("active")) {
				activeInventory = "Yes";
			} else if (inventoryType.equalsIgnoreCase("low")) {
				lowInventory = "Yes";
			} else if (inventoryType.equalsIgnoreCase("out")) {
				outOfInventory = "Yes";
			} else if (inventoryType.equalsIgnoreCase("discontinue")) {
				disContinue = "Yes";
			}
		} else {
			activeInventory = "Yes";
		}

		Page<ProductEntity> productPages = productRepository.findAll(pageable, categoryId, fromDate, toDate, name,
				activeInventory, lowInventory, outOfInventory, disContinue);
		totalPages = productPages.getTotalPages();
		pageRecords = productPages.getNumberOfElements();
		totalRecords = (int) productPages.getTotalElements();
		enList = productPages.getContent();

		List<ProductResDTO> productList = enList.stream().map(x -> {
			ProductResDTO dto = new ProductResDTO();
			BeanUtils.copyProperties(x, dto);
			dto.setCreatedDate(Utility.getStringFromDate(x.getCreatedDate(), true));
			try {
				CategoryEntity category = categoryRepository.findById(Long.valueOf(x.getCategory()))
						.orElseThrow(() -> new ValidationException("dataNotFoundCode", "CategoryNotFoundDesc",
								"successBooleanFalse"));
				dto.setCategoryName(category.getName());
			} catch (Exception e1) {
				dto.setCategoryName("");
			}
			dto.setBarCode(x.getBarCode());

			List<ProductPricingResDTO> productPriceDetails = new ArrayList<>();
			for (ProductPriceEntity priceEn : x.getProductPriceEntity()) {
				ProductPricingResDTO priceDTO = new ProductPricingResDTO();
				priceDTO.setCostPrice(priceEn.getCostPrice());
				priceDTO.setMargin(priceEn.getMargin());
				priceDTO.setPriceTypeId(priceEn.getPriceType());
				priceDTO.setUnit(priceEn.getUnit());
				priceDTO.setSellingPrice(priceEn.getSellingPrice());
				priceDTO.setTaxId(priceEn.getTax());
				priceDTO.setVendorNameId(priceEn.getVendorName());
				try {
					VendorEntity vendor = vendorRepository.findById(Long.valueOf(priceEn.getVendorName()))
							.orElseThrow(() -> new ValidationException("dataNotFoundCode", "vendorNotFoundDesc",
									"successBooleanFalse"));
					priceDTO.setVendorName(vendor.getName());
				} catch (Exception e) {
					priceDTO.setVendorName("");
				}

				productPriceDetails.add(priceDTO);
			}

			List<ProductStockResDTO> productStockDetails = new ArrayList<>();
			for (ProductStockEntity stockEn : x.getProductStockEntity()) {
				ProductStockResDTO stockDTO = new ProductStockResDTO();
				stockDTO.setParLevel(stockEn.getParLevel());
				stockDTO.setReorderQty(stockEn.getReorderQuantity());
				stockDTO.setStockQty(stockEn.getQuantity());
				productStockDetails.add(stockDTO);
			}

			List<ProductOtherDetailsResDTO> productOtherDetails = new ArrayList<>();
			for (ProductOtherDetailsEntity otherDtlsEn : x.getProductOtherDetailsEntity()) {
				ProductOtherDetailsResDTO otherDtlsDto = new ProductOtherDetailsResDTO();
				otherDtlsDto.setAlertBeforeDays(otherDtlsEn.getAlertBeforeDays());
				otherDtlsDto.setEbtEnabled(otherDtlsEn.getEbtEnabled());
				otherDtlsDto.setExpiryDate(Utility.getStringFromDate(otherDtlsEn.getPromotionExpiryDate(), false));
				otherDtlsDto.setPromotionId(otherDtlsEn.getPromotions());
				otherDtlsDto.setQtyDetails(otherDtlsEn.getQuantityDetails());

				List<ProductPackResDTO> productPackDetails = new ArrayList<>();
				for (ProductPackEntity packEn : x.getProductPackEntity()) {
					ProductPackResDTO packDTO = new ProductPackResDTO();
					packDTO.setId(packEn.getId());
					packDTO.setBarCode(packEn.getBarCode());
					packDTO.setCaseCost(packEn.getCaseCost());
					packDTO.setCaseParLevel(packEn.getCaseParLevel());
					packDTO.setCasePrice(packEn.getCasePrice());
					packDTO.setCaseReorderQty(packEn.getCaseReOrderQty());
					packDTO.setPackName(packEn.getName());
					packDTO.setSinglePerCase(packEn.getSinglePerCase());
					packDTO.setSku(packEn.getSku());
					packDTO.setTotalPacksPerCase(packEn.getTotalPacksPerCase());
					productPackDetails.add(packDTO);
				}

				otherDtlsDto.setProductPacks(productPackDetails);
				productOtherDetails.add(otherDtlsDto);
			}

			List<ProductTagsResDTO> productTagsList = new ArrayList<>();
			for (ProductTagsEntity tagsEn : x.getProductTags()) {
				ProductTagsResDTO tagsDTO = new ProductTagsResDTO();
				BeanUtils.copyProperties(tagsEn, tagsDTO);
				productTagsList.add(tagsDTO);
			}

			if (productOtherDetails.size() > 0)
				dto.setProductOtherDetails(productOtherDetails.get(0));
			if (productPriceDetails.size() > 0)
				dto.setProductPriceDetails(productPriceDetails.get(0));
			if (productStockDetails.size() > 0)
				dto.setProductStockDetails(productStockDetails.get(0));
			dto.setProductTags(productTagsList);
			return dto;
		}).collect(Collectors.toList());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		if (productList == null || productList.size() < 1)
			response.setCommonMessage("Product Not Found");
		response.setProducts(productList);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;
	}

	@Override
	public APIResponse uploadFile(@RequestParam("file") MultipartFile file) throws ValidationException {
		String fileUrl = amazonClient.uploadFile(file);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setProductImageS3Url(fileUrl);
		return response;
	}

	@Override
	public APIResponse getProductById(Long id) throws ValidationException {

		ProductEntity x = productRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "ProductNotFoundDesc", "successBooleanFalse"));

		ProductResDTO dto = new ProductResDTO();
		BeanUtils.copyProperties(x, dto);

		List<ProductPricingResDTO> productPriceDetails = new ArrayList<>();
		for (ProductPriceEntity priceEn : x.getProductPriceEntity()) {
			ProductPricingResDTO priceDTO = new ProductPricingResDTO();
			priceDTO.setCostPrice(priceEn.getCostPrice());
			priceDTO.setMargin(priceEn.getMargin());
			priceDTO.setPriceTypeId(priceEn.getPriceType());
			priceDTO.setUnit(priceEn.getUnit());
			priceDTO.setSellingPrice(priceEn.getSellingPrice());
			priceDTO.setTaxId(priceEn.getTax());
			priceDTO.setVendorNameId(priceEn.getVendorName());
			productPriceDetails.add(priceDTO);
		}

		List<ProductStockResDTO> productStockDetails = new ArrayList<>();
		for (ProductStockEntity stockEn : x.getProductStockEntity()) {
			ProductStockResDTO stockDTO = new ProductStockResDTO();
			stockDTO.setParLevel(stockEn.getParLevel());
			stockDTO.setReorderQty(stockEn.getReorderQuantity());
			stockDTO.setStockQty(stockEn.getQuantity());
			productStockDetails.add(stockDTO);
		}

		List<ProductOtherDetailsResDTO> productOtherDetails = new ArrayList<>();
		for (ProductOtherDetailsEntity otherDtlsEn : x.getProductOtherDetailsEntity()) {
			ProductOtherDetailsResDTO otherDtlsDto = new ProductOtherDetailsResDTO();
			otherDtlsDto.setAlertBeforeDays(otherDtlsEn.getAlertBeforeDays());
			otherDtlsDto.setEbtEnabled(otherDtlsEn.getEbtEnabled());
			otherDtlsDto.setExpiryDate(Utility.getStringFromDate(otherDtlsEn.getPromotionExpiryDate(), false));
			otherDtlsDto.setPromotionId(otherDtlsEn.getPromotions());
			otherDtlsDto.setQtyDetails(otherDtlsEn.getQuantityDetails());

			List<ProductPackResDTO> productPackDetails = new ArrayList<>();
			for (ProductPackEntity packEn : x.getProductPackEntity()) {
				ProductPackResDTO packDTO = new ProductPackResDTO();
				packDTO.setId(packEn.getId());
				packDTO.setBarCode(packEn.getBarCode());
				packDTO.setCaseCost(packEn.getCaseCost());
				packDTO.setCaseParLevel(packEn.getCaseParLevel());
				packDTO.setCasePrice(packEn.getCasePrice());
				packDTO.setCaseReorderQty(packEn.getCaseReOrderQty());
				packDTO.setPackName(packEn.getName());
				packDTO.setSinglePerCase(packEn.getSinglePerCase());
				packDTO.setSku(packEn.getSku());
				packDTO.setTotalPacksPerCase(packEn.getTotalPacksPerCase());
				productPackDetails.add(packDTO);
			}

			otherDtlsDto.setProductPacks(productPackDetails);
			productOtherDetails.add(otherDtlsDto);
		}

		List<ProductTagsResDTO> productTagsList = new ArrayList<>();
		for (ProductTagsEntity tagsEn : x.getProductTags()) {
			ProductTagsResDTO tagsDTO = new ProductTagsResDTO();
			BeanUtils.copyProperties(tagsEn, tagsDTO);
			productTagsList.add(tagsDTO);
		}

		dto.setProductOtherDetails(productOtherDetails.get(0));
		dto.setProductPriceDetails(productPriceDetails.get(0));
		dto.setProductStockDetails(productStockDetails.get(0));
		dto.setProductTags(productTagsList);

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");

		response.setProduct(dto);
		return response;
	}

	@Override
	public APIResponse updateProduct(Long id, ProductReqDTO dto) throws ValidationException {
		ProductEntity product = productRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "ProductNotFoundDesc", "successBooleanFalse"));

		List<ProductEntity> products = productRepository.getProductByName(dto.getName().trim());
		if (products.size() > 0 && products.get(0).getId().intValue() != id.intValue())
			throw new ValidationException("dataAlreadyExistCode", "ProductAlreadyExist", "successBooleanFalse");

		// // Check BarCode and SKU is present or not for product as well
		// // product pack
		checkBarodeAndSKUIsUniqueForProduct(dto, id);

		if (dto.getShowPOS() != null)
			product.setPosFlag(dto.getShowPOS() ? 1 : 0);
		if (dto.getName() != null)
			product.setName(dto.getName());
		if (dto.getProductImageURL() != null)
			product.setImageURL(dto.getProductImageURL());
		if (dto.getCategoryId() != null)
			product.setCategory(dto.getCategoryId());
		if (dto.getBarCode() != null) {
			product.setBarCode(dto.getBarCode());
		}
		if (dto.getSku() != null) {

			product.setSKU(dto.getSku());
		}
		if (dto.getProductDesc() != null)
			product.setDesc(dto.getProductDesc());
		if (dto.getSizeId() != null)
			product.setSize(dto.getSizeId());
		if (dto.getVolume() != null)
			product.setVolume(dto.getVolume());

		if (dto.getTags() != null && !dto.getTags().isEmpty()) {
			Set<ProductTagsEntity> tagsEntitySet = new HashSet<ProductTagsEntity>();
			String[] tagsArr = dto.getTags().split(",");
			for (String tag : tagsArr) {
				ProductTagsEntity tagEn = new ProductTagsEntity();
				tagEn.setDesc(tag);
				tagsEntitySet.add(tagEn);
			}
			if (tagsEntitySet.size() > 0) {
				// Delete Existing tags
				productRepository.deleteProductTagsByProductId(id.intValue());
			}
			product.setProductTags(tagsEntitySet);
		} else {
			productRepository.deleteProductTagsByProductId(id.intValue());
		}

		if (dto.getProductPricing() != null) {
			Set<ProductPriceEntity> priceEntitySet = new HashSet<ProductPriceEntity>();
			ProductPriceEntity priceEn = null;
			for (ProductPriceEntity en : product.getProductPriceEntity()) {
				priceEn = en;
			}
			if (priceEn == null)
				priceEn = new ProductPriceEntity();
			if (dto.getProductPricing().getCostPrice() != null)
				priceEn.setCostPrice(dto.getProductPricing().getCostPrice());
			if (dto.getProductPricing().getMargin() != null)
				priceEn.setMargin(dto.getProductPricing().getMargin());
			if (dto.getProductPricing().getPriceTypeId() != null)
				priceEn.setPriceType(dto.getProductPricing().getPriceTypeId());
			if (dto.getProductPricing().getUnit() != null)
				priceEn.setUnit(dto.getProductPricing().getUnit());
			if (dto.getProductPricing().getSellingPrice() != null) {
				priceEn.setOldSellingPrice(priceEn.getSellingPrice());
				priceEn.setPriceUpdateDate(new Date());
				priceEn.setSellingPrice(dto.getProductPricing().getSellingPrice());
			}
			if (dto.getProductPricing().getTaxId() != null)
				priceEn.setTax(dto.getProductPricing().getTaxId());
			if (dto.getProductPricing().getVendorNameId() != null)
				priceEn.setVendorName(dto.getProductPricing().getVendorNameId());
			priceEntitySet.add(priceEn);
			product.setProductPriceEntity(priceEntitySet);
		}

		if (dto.getProductStockDetails() != null) {
			Set<ProductStockEntity> stockEntitySet = new HashSet<ProductStockEntity>();
			ProductStockEntity stockEn = null;
			for (ProductStockEntity en : product.getProductStockEntity()) {
				stockEn = en;
			}
			if (stockEn == null)
				stockEn = new ProductStockEntity();

			if (dto.getProductStockDetails().getStockQty() != null)
				stockEn.setQuantity(dto.getProductStockDetails().getStockQty());
			if (dto.getProductStockDetails().getReorderQty() != null)
				stockEn.setReorderQuantity(dto.getProductStockDetails().getReorderQty());
			if (dto.getProductStockDetails().getParLevel() != null)
				stockEn.setParLevel(dto.getProductStockDetails().getParLevel());
			stockEntitySet.add(stockEn);
			product.setProductStockEntity(stockEntitySet);
		}

		if (dto.getProductOtherDetails() != null) {
			Set<ProductOtherDetailsEntity> otherDtlsEntitySet = new HashSet<ProductOtherDetailsEntity>();
			ProductOtherDetailsEntity dtlsEn = null;
			for (ProductOtherDetailsEntity en : product.getProductOtherDetailsEntity()) {
				dtlsEn = en;
			}
			if (dtlsEn == null)
				dtlsEn = new ProductOtherDetailsEntity();

			if (dto.getProductOtherDetails().getQtyDetails() != null)
				dtlsEn.setQuantityDetails(dto.getProductOtherDetails().getQtyDetails());

			if (dto.getProductOtherDetails().getPromotionId() != null
					&& dto.getProductOtherDetails().getPromotionId() > 0)
				dtlsEn.setPromotions(dto.getProductOtherDetails().getPromotionId());

			if (dto.getProductOtherDetails().getExpiryDate() != null)
				dtlsEn.setPromotionExpiryDate(Utility.getDateFromString(dto.getProductOtherDetails().getExpiryDate()));
			if (dto.getProductOtherDetails().getAlertBeforeDays() != null)
				dtlsEn.setAlertBeforeDays(dto.getProductOtherDetails().getAlertBeforeDays());
			if (dto.getProductOtherDetails().getEbtEnabled() != null)
				dtlsEn.setEbtEnabled(dto.getProductOtherDetails().getEbtEnabled());
			otherDtlsEntitySet.add(dtlsEn);
			product.setProductOtherDetailsEntity(otherDtlsEntitySet);

			if (dto.getProductOtherDetails() != null && dto.getProductOtherDetails().getProductPacks() != null) {
				// check productPack barcode and SKU
				checkBarodeAndSKUIsUniqueForProduct(dto, id);

				List<ProductPackReqDTO> productPacksReq = dto.getProductOtherDetails().getProductPacks();
				Set<ProductPackEntity> productPacks = new HashSet<ProductPackEntity>();
				for (ProductPackReqDTO packDTO : productPacksReq) {
					ProductPackEntity packEn = new ProductPackEntity();
					packEn.setName(packDTO.getPackName());
					packEn.setTotalPacksPerCase(packDTO.getTotalPacksPerCase());
					packEn.setSinglePerCase(packDTO.getSinglePerCase());
					packEn.setCaseCost(packDTO.getCaseCost());
					packEn.setCasePrice(packDTO.getCasePrice());
					packEn.setCaseParLevel(packDTO.getCaseParLevel());
					packEn.setCaseReOrderQty(packDTO.getCaseReorderQty());
					packEn.setSku(packDTO.getSku());
					packEn.setBarCode(packDTO.getBarCode());
					productPacks.add(packEn);
				}
				if (productPacks.size() > 0) {
					// Delete existing packs
					productRepository.deleteProductPacksByProductId(id.intValue());
				}

				product.setProductPackEntity(productPacks);
			}
		}
		productRepository.save(product);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse getProductByBarCodeId(String barcodeNo) {

		List<ProductEntity> productEnList = productRepository.getProductByBarCodeId(barcodeNo.trim());
		List<ProductResDTO> productList = productEnList.stream().map(x -> {
			ProductResDTO dto = new ProductResDTO();
			BeanUtils.copyProperties(x, dto);

			List<ProductPricingResDTO> productPriceDetails = new ArrayList<>();
			for (ProductPriceEntity priceEn : x.getProductPriceEntity()) {
				ProductPricingResDTO priceDTO = new ProductPricingResDTO();
				priceDTO.setCostPrice(priceEn.getCostPrice());
				priceDTO.setMargin(priceEn.getMargin());
				priceDTO.setPriceTypeId(priceEn.getPriceType());
				priceDTO.setUnit(priceEn.getUnit());
				priceDTO.setSellingPrice(priceEn.getSellingPrice());
				priceDTO.setTaxId(priceEn.getTax());
				priceDTO.setVendorNameId(priceEn.getVendorName());
				productPriceDetails.add(priceDTO);
			}

			List<ProductStockResDTO> productStockDetails = new ArrayList<>();
			for (ProductStockEntity stockEn : x.getProductStockEntity()) {
				ProductStockResDTO stockDTO = new ProductStockResDTO();
				stockDTO.setParLevel(stockEn.getParLevel());
				stockDTO.setReorderQty(stockEn.getReorderQuantity());
				stockDTO.setStockQty(stockEn.getQuantity());
				productStockDetails.add(stockDTO);
			}

			List<ProductOtherDetailsResDTO> productOtherDetails = new ArrayList<>();
			for (ProductOtherDetailsEntity otherDtlsEn : x.getProductOtherDetailsEntity()) {
				ProductOtherDetailsResDTO otherDtlsDto = new ProductOtherDetailsResDTO();
				otherDtlsDto.setAlertBeforeDays(otherDtlsEn.getAlertBeforeDays());
				otherDtlsDto.setEbtEnabled(otherDtlsEn.getEbtEnabled());
				otherDtlsDto.setExpiryDate(Utility.getStringFromDate(otherDtlsEn.getPromotionExpiryDate(), false));
				otherDtlsDto.setPromotionId(otherDtlsEn.getPromotions());
				otherDtlsDto.setQtyDetails(otherDtlsEn.getQuantityDetails());

				List<ProductPackResDTO> productPackDetails = new ArrayList<>();
				for (ProductPackEntity packEn : x.getProductPackEntity()) {
					ProductPackResDTO packDTO = new ProductPackResDTO();
					packDTO.setId(packEn.getId());
					packDTO.setBarCode(packEn.getBarCode());
					packDTO.setCaseCost(packEn.getCaseCost());
					packDTO.setCaseParLevel(packEn.getCaseParLevel());
					packDTO.setCasePrice(packEn.getCasePrice());
					packDTO.setCaseReorderQty(packEn.getCaseReOrderQty());
					packDTO.setPackName(packEn.getName());
					packDTO.setSinglePerCase(packEn.getSinglePerCase());
					packDTO.setSku(packEn.getSku());
					packDTO.setTotalPacksPerCase(packEn.getTotalPacksPerCase());
					productPackDetails.add(packDTO);
				}

				otherDtlsDto.setProductPacks(productPackDetails);
				productOtherDetails.add(otherDtlsDto);
			}

			List<ProductTagsResDTO> productTagsList = new ArrayList<>();
			for (ProductTagsEntity tagsEn : x.getProductTags()) {
				ProductTagsResDTO tagsDTO = new ProductTagsResDTO();
				BeanUtils.copyProperties(tagsEn, tagsDTO);
				productTagsList.add(tagsDTO);
			}

			dto.setProductOtherDetails(productOtherDetails.get(0));
			dto.setProductPriceDetails(productPriceDetails.get(0));
			dto.setProductStockDetails(productStockDetails.get(0));
			dto.setProductTags(productTagsList);
			return dto;
		}).collect(Collectors.toList());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setProducts(productList);
		if (productList == null || productList.size() < 1)
			response.setCommonMessage("Product Not Found");

		return response;

	}

	@Override
	public APIResponse getProductByName(Pageable pageable, String name) throws ValidationException {

		Integer totalPages = 0;
		Integer pageRecords = 0;
		Integer totalRecords = 0;
		List<ProductEntity> enList = null;
		// List<ProductEntity> enList =
		// productRepository.getProductByName(pageable,name.trim().toLowerCase());

		Page<ProductEntity> productPages = productRepository.getProductByName(pageable, name.trim().toLowerCase());
		totalPages = productPages.getTotalPages();
		pageRecords = productPages.getNumberOfElements();
		totalRecords = (int) productPages.getTotalElements();
		enList = productPages.getContent();

		List<ProductResDTO> productList = enList.stream().map(x -> {
			ProductResDTO dto = new ProductResDTO();
			BeanUtils.copyProperties(x, dto);

			List<ProductPricingResDTO> productPriceDetails = new ArrayList<>();
			for (ProductPriceEntity priceEn : x.getProductPriceEntity()) {
				ProductPricingResDTO priceDTO = new ProductPricingResDTO();
				priceDTO.setCostPrice(priceEn.getCostPrice());
				priceDTO.setMargin(priceEn.getMargin());
				priceDTO.setPriceTypeId(priceEn.getPriceType());
				priceDTO.setUnit(priceEn.getUnit());
				priceDTO.setSellingPrice(priceEn.getSellingPrice());
				priceDTO.setTaxId(priceEn.getTax());
				priceDTO.setVendorNameId(priceEn.getVendorName());
				productPriceDetails.add(priceDTO);
			}

			List<ProductStockResDTO> productStockDetails = new ArrayList<>();
			for (ProductStockEntity stockEn : x.getProductStockEntity()) {
				ProductStockResDTO stockDTO = new ProductStockResDTO();
				stockDTO.setParLevel(stockEn.getParLevel());
				stockDTO.setReorderQty(stockEn.getReorderQuantity());
				stockDTO.setStockQty(stockEn.getQuantity());
				productStockDetails.add(stockDTO);
			}

			List<ProductOtherDetailsResDTO> productOtherDetails = new ArrayList<>();
			for (ProductOtherDetailsEntity otherDtlsEn : x.getProductOtherDetailsEntity()) {
				ProductOtherDetailsResDTO otherDtlsDto = new ProductOtherDetailsResDTO();
				otherDtlsDto.setAlertBeforeDays(otherDtlsEn.getAlertBeforeDays());
				otherDtlsDto.setEbtEnabled(otherDtlsEn.getEbtEnabled());
				otherDtlsDto.setExpiryDate(Utility.getStringFromDate(otherDtlsEn.getPromotionExpiryDate(), false));
				otherDtlsDto.setPromotionId(otherDtlsEn.getPromotions());
				otherDtlsDto.setQtyDetails(otherDtlsEn.getQuantityDetails());

				List<ProductPackResDTO> productPackDetails = new ArrayList<>();
				for (ProductPackEntity packEn : x.getProductPackEntity()) {
					ProductPackResDTO packDTO = new ProductPackResDTO();
					packDTO.setId(packEn.getId());
					packDTO.setBarCode(packEn.getBarCode());
					packDTO.setCaseCost(packEn.getCaseCost());
					packDTO.setCaseParLevel(packEn.getCaseParLevel());
					packDTO.setCasePrice(packEn.getCasePrice());
					packDTO.setCaseReorderQty(packEn.getCaseReOrderQty());
					packDTO.setPackName(packEn.getName());
					packDTO.setSinglePerCase(packEn.getSinglePerCase());
					packDTO.setSku(packEn.getSku());
					packDTO.setTotalPacksPerCase(packEn.getTotalPacksPerCase());
					productPackDetails.add(packDTO);
				}

				otherDtlsDto.setProductPacks(productPackDetails);
				productOtherDetails.add(otherDtlsDto);
			}

			List<ProductTagsResDTO> productTagsList = new ArrayList<>();
			for (ProductTagsEntity tagsEn : x.getProductTags()) {
				ProductTagsResDTO tagsDTO = new ProductTagsResDTO();
				BeanUtils.copyProperties(tagsEn, tagsDTO);
				productTagsList.add(tagsDTO);
			}

			if (productOtherDetails.size() > 0)
				dto.setProductOtherDetails(productOtherDetails.get(0));
			if (productPriceDetails.size() > 0)
				dto.setProductPriceDetails(productPriceDetails.get(0));
			if (productStockDetails.size() > 0)
				dto.setProductStockDetails(productStockDetails.get(0));
			dto.setProductTags(productTagsList);
			return dto;
		}).collect(Collectors.toList());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setProducts(productList);
		if (productList == null || productList.size() < 1)
			response.setCommonMessage("Product Not Found");
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;

	}

	@Override
	public APIResponse updateProductPack(Long id, ProductPackReqDTO productPackReqDTO) throws ValidationException {
		ProductPackEntity productPack = productPackRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "ProductPackNotFoundDesc", "successBooleanFalse"));

		if (productPack.getProduct() == null || productPack.getProduct().getId() < 1) {
			throw new ValidationException("dataNotFoundCode", "ProductNotFoundDesc", "successBooleanFalse");
		}
		ProductEntity product = productRepository.findById(productPack.getProduct().getId()).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "ProductPackNotFoundDesc", "successBooleanFalse"));
		if (product.getIsActive() < 1) {
			throw new ValidationException("dataNotFoundCode", "ProductPackNotFoundDesc", "successBooleanFalse");
		}

		if (productPackReqDTO.getBarCode() != null)
			productPack.setBarCode(productPackReqDTO.getBarCode());
		if (productPackReqDTO.getPackName() != null)
			productPack.setName(productPackReqDTO.getPackName());
		if (productPackReqDTO.getTotalPacksPerCase() != null && productPackReqDTO.getTotalPacksPerCase() > 0)
			productPack.setTotalPacksPerCase(productPackReqDTO.getTotalPacksPerCase());
		if (productPackReqDTO.getSinglePerCase() != null && productPackReqDTO.getSinglePerCase() > 0)
			productPack.setSinglePerCase(productPackReqDTO.getSinglePerCase());
		if (productPackReqDTO.getCaseCost() != null && productPackReqDTO.getCaseCost() > 0)
			productPack.setCaseCost(productPackReqDTO.getCaseCost());
		if (productPackReqDTO.getCasePrice() != null && productPackReqDTO.getCasePrice() > 0)
			productPack.setCasePrice(productPackReqDTO.getCasePrice());
		if (productPackReqDTO.getCaseParLevel() != null && productPackReqDTO.getCaseParLevel() > 0)
			productPack.setCaseParLevel(productPackReqDTO.getCaseParLevel());
		if (productPackReqDTO.getCaseReorderQty() != null && productPackReqDTO.getCaseReorderQty() > 0)
			productPack.setCaseReOrderQty(productPackReqDTO.getCaseReorderQty());
		if (productPackReqDTO.getSku() != null)
			productPack.setSku(productPackReqDTO.getSku());

		productPackRepository.save(productPack);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse deleteProductPack(Long id) throws ValidationException {
		ProductPackEntity productPack = productPackRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "ProductPackNotFoundDesc", "successBooleanFalse"));

		ProductEntity product = productRepository.findById(productPack.getProduct().getId()).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "ProductPackNotFoundDesc", "successBooleanFalse"));
		if (product.getIsActive() < 1) {
			throw new ValidationException("dataNotFoundCode", "ProductPackNotFoundDesc", "successBooleanFalse");
		}
		productPackRepository.delete(productPack);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse stockAdjustment(StockAdjustmentReqDTO reqDTO) throws ValidationException {
		// Check ProductID is valid
		ProductEntity product = productRepository.findById(reqDTO.getProductId()).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "ProductNotFoundDesc", "successBooleanFalse"));
		ProductStockEntity productStock = null;
		for (ProductStockEntity stockEntity : product.getProductStockEntity()) {
			productStock = stockEntity;
		}
		if (productStock != null) {
			int newStock = productStock.getQuantity();
			if (reqDTO.getAdd() != null && reqDTO.getAdd()) {
				newStock = newStock + reqDTO.getAdjustedStock();
			} else if (reqDTO.getRemove() != null && reqDTO.getRemove()) {
				newStock = newStock - reqDTO.getAdjustedStock();
			}
			productStock.setQuantity(newStock);
			productStockRepository.save(productStock);
		}
		// Save into stockadjust table
		ProductStockAdjustementEntity adjustEntity = new ProductStockAdjustementEntity();
		adjustEntity.setAdjustedStock(reqDTO.getAdjustedStock());
		adjustEntity.setProductId(product.getId().intValue());
		adjustEntity.setAdjustedDate(new Date());
		adjustEntity.setReason(reqDTO.getReason());
		productStockAdjustmentRepository.save(adjustEntity);

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse getStockAdjustment(Pageable pageable, Long id) {
		Integer totalPages = 0;
		Integer pageRecords = 0;
		Integer totalRecords = 0;
		List<ProductStockAdjustementEntity> enList = null;

		Page<ProductStockAdjustementEntity> pages = productStockAdjustmentRepository.findAll(pageable, id);
		totalPages = pages.getTotalPages();
		pageRecords = pages.getNumberOfElements();
		totalRecords = (int) pages.getTotalElements();
		enList = pages.getContent();

		List<ProductStockAdjustmentResDTO> list = enList.stream().map(x -> {
			ProductStockAdjustmentResDTO dto = new ProductStockAdjustmentResDTO();
			BeanUtils.copyProperties(x, dto);
			dto.setAdjustedDate(Utility.getStringFromDate(x.getAdjustedDate(), true));
			return dto;
		}).collect(Collectors.toList());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");

		response.setStockAdjustment(list);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;
	}

}
