package com.in.serviceimpl;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.entity.POSOrderEntity;
import com.in.entity.POSOrderItemsEntity;
import com.in.entity.ProductEntity;
import com.in.entity.ProductOtherDetailsEntity;
import com.in.entity.ProductPackEntity;
import com.in.entity.ProductStockEntity;
import com.in.entity.PromotionEntity;
import com.in.entity.PromotionTypeEntity;
import com.in.entity.PromotionTypeProductMapEntity;
import com.in.entity.TaxEntity;
import com.in.exception.ValidationException;
import com.in.repository.POSOrderRepository;
import com.in.repository.ProductPackRepository;
import com.in.repository.ProductRepository;
import com.in.repository.ProductStockRepository;
import com.in.repository.PromotionRepository;
import com.in.repository.TaxRepository;
import com.in.request.dto.POSOrderItemsReqDTO;
import com.in.request.dto.POSOrderReqDTO;
import com.in.service.POSOrderService;
import com.in.util.Utility;

@Service
public class POSOrderServiceImpl implements POSOrderService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	PromotionRepository promotionRepository;

	@Autowired
	POSOrderRepository posOrderRepository;

	@Autowired
	TaxRepository taxRepository;

	@Autowired
	ProductStockRepository productStockRepository;

	@Autowired
	ProductPackRepository productPackRepository;
	
	@Autowired
	PushNotificationServiceImpl pushNotification;

	@Override
	public APIResponse savePOSOrder(POSOrderReqDTO dto) throws ValidationException {

		if (dto.getPOSOrderItems() != null && !dto.getPOSOrderItems().isEmpty()) {
			POSOrderEntity posOrderEntity = new POSOrderEntity();
			posOrderEntity.setCustId(dto.getCustId());
			Set<POSOrderItemsEntity> posOrderItems = new HashSet<>();

			for (POSOrderItemsReqDTO orderDTO : dto.getPOSOrderItems()) {
				Optional<ProductEntity> en = productRepository.findById(orderDTO.getProductId());
				if (en.isPresent()) {

					if (orderDTO.getSizeId() != null && orderDTO.getSizeId() != 0) {
						if (orderDTO.getSizeId() != en.get().getSize()) {
							throw new ValidationException("dataNotFoundCode", "inValidSizeDesc", "successBooleanFalse");
						}
					}
					// Get tax id for the product and get the tax value
					long taxId = 0;
					double taxPercentage = 0.0, discountAmount = 0.0;
					if (en.get().getProductPriceEntity() != null) {
						Integer productTaxId = en.get().getProductPriceEntity().iterator().next().getTax();
						if (productTaxId != null) {
							taxId = productTaxId;
						}
					}

					if (taxId != 0) {
						// Get tax details from the Tax table
						TaxEntity taxDetails = taxRepository.findById(Long.valueOf(taxId))
								.orElseThrow(() -> new ValidationException("dataNotFoundCode", "inValidTaxIdMappedDesc",
										"successBooleanFalse"));
						taxPercentage = taxDetails.getAmount();
					}

					if (orderDTO.getIsPack() == null || orderDTO.getIsPack() <= 0) {
						// Check have enough stock to sell
						if (en.get().getProductStockEntity() != null) {
							ProductStockEntity stock = en.get().getProductStockEntity().iterator().next();
							if (stock.getQuantity() < orderDTO.getQty()) {
								throw new ValidationException("dataNotFoundCode", "noStockFoundForProduct", "successBooleanFalse");
							}
						}
						
						// Call apply promotion method
						discountAmount = applyPromotions(orderDTO, en);
						deductFromStock(orderDTO.getQty(), orderDTO.getProductId().intValue(), en);
					}

					// Calculate price
					// if size id is available then consider price from
					// product:price
					Double price = 0.0, actualCost = 0.0, tax = 0.0, profit = 0.0, total = 0.0;
					if (orderDTO.getIsPack() > 0) {
						// Check pack if is valid or not
						ProductPackEntity productPackEntity = productPackRepository.findById(new Long(orderDTO.getPackId())).orElseThrow(
								() -> new ValidationException("dataNotFoundCode", "productPackIDIsNotvalid", "successBooleanFalse"));
						
						if(productPackEntity.getTotalPacksPerCase() < 1){
							throw new ValidationException("dataNotFoundCode", "productPackIsOutofStock", "successBooleanFalse");							
						}
						
						if(productPackEntity.getTotalPacksPerCase() < orderDTO.getQty()){
							throw new ValidationException("dataNotFoundCode", "noEnoughStockAvailble", "successBooleanFalse");							
						}

						deductFromPack(orderDTO.getQty(), orderDTO.getPackId(), en);
						price = productPackEntity.getCasePrice();
						actualCost = productPackEntity.getCaseCost();

					} else {
						if (orderDTO.getUnit() != null && orderDTO.getUnit() > 0) {
							price = orderDTO.getUnit()
									* en.get().getProductPriceEntity().iterator().next().getSellingPrice();
							actualCost = orderDTO.getUnit()
									* en.get().getProductPriceEntity().iterator().next().getCostPrice();
						} else {
							price = orderDTO.getQty()
									* en.get().getProductPriceEntity().iterator().next().getSellingPrice();
							actualCost = orderDTO.getQty()
									* en.get().getProductPriceEntity().iterator().next().getCostPrice();
						}
					}
					if (price > 0) {
						tax = (price * taxPercentage) / 100.0;
					}
					profit = price - actualCost;
					total = tax + price - discountAmount;

					POSOrderItemsEntity posOrderItemEn = new POSOrderItemsEntity();
					posOrderItemEn.setIsPack(0);
					posOrderItemEn.setLoss(profit < 0 ? Math.abs(profit) : 0.0);
					posOrderItemEn.setProductId(orderDTO.getProductId().intValue());
					posOrderItemEn.setProfit(profit > 0 ? profit : 0.0);
					posOrderItemEn.setPromotionAmount(0.0);
					posOrderItemEn.setQty(orderDTO.getQty());
					posOrderItemEn.setTax(tax);
					posOrderItemEn.setTotal(total);
					posOrderItemEn.setSaleDate(new Date());
					posOrderItemEn.setPromotionAmount(discountAmount);
					posOrderItems.add(posOrderItemEn);
				} else {
					throw new ValidationException("dataNotFoundCode", "ProductNotFoundDesc", "successBooleanFalse");
				}
			}
			posOrderEntity.setPosOrderItems(posOrderItems);
			posOrderRepository.save(posOrderEntity);
		}
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	private Double applyPromotions(POSOrderItemsReqDTO orderDTO, Optional<ProductEntity> en)
			throws ValidationException {

		Integer promotionId = null;
		Iterator<ProductOtherDetailsEntity> it = en.get().getProductOtherDetailsEntity().iterator();
		if (it.hasNext()) {
			promotionId = it.next().getPromotions();
		}

		if (promotionId != null) {
			Optional<PromotionEntity> promotionEn = promotionRepository.findById((long) promotionId);
			PromotionEntity promotion = null;
			if (promotionEn.isPresent()) {
				promotion = promotionEn.get();
				// If promotion is active
				if (promotion.getStatusId() == 1) {
					// Check if promotion is not expired
					Date currentDate = new Date(), promotionStartDate = promotion.getStartDate(),
							promotionEndDate = promotion.getEndDate();
					if (currentDate.after(promotionStartDate) && currentDate.before(promotionEndDate)) {
						if (promotion.getPromotionType() != null) {
							PromotionTypeEntity promotionType = promotion.getPromotionType().iterator().next();
							if (promotionType != null) {
								if (promotionType.getApplyId() == 1) {// BOGO
									// If promotion product == buying product
									if (promotionType.getProductId() == en.get().getId().intValue()
											&& orderDTO.getQty() == promotionType.getBuy()) {
										// Then need to remove from stock
										int freeNoProduct = (int) promotionType.getGetAmount().doubleValue();
										Integer freeProductId = promotionType.getOf();
										deductFromStock(freeNoProduct, freeProductId, en);
									}
								} else if (promotionType.getApplyId() == 2) {// Mix
																				// and
																				// Match
									// Fetch all the productIds set from
									// PrmotionProductMapping
									Set<PromotionTypeProductMapEntity> productSet = promotion
											.getPromotionTypeProductMap();
									Set<Integer> productIdSet = productSet.stream().map(x -> x.getProductId())
											.collect(Collectors.toSet());
									// if
									// (productIdSet.contains(orderDTO.getProductId()))
									// {
									if (promotionType.getDiscountOnEvery() == 1) {// Buy
																					// more
																					// than

										if (orderDTO.getQty() > promotionType.getBuyingUnit()
												&& orderDTO.getQty() < promotionType.getLimitTo()) {
											// give discount
											return promotionType.getDiscountAmount();
										}
									} else if (promotionType.getDiscountOnEvery() == 2) {// Buy
																							// multiply

										if (orderDTO.getQty() < promotionType.getLimitTo()
												&& orderDTO.getQty() != promotionType.getLimitTo()
												&& orderDTO.getQty() % promotionType.getLimitTo() == 0) {
											// give discount
											return promotionType.getDiscountAmount();
										}
									}
									// }
								}
							}
						}
					}
				}
			}
		}
		return 0.0;
	}

	private void deductFromStock(int noOfProduct, Integer productId, Optional<ProductEntity> en)
			throws ValidationException {
		// Get Stock Details
		if (en.get().getProductStockEntity() != null) {
			ProductStockEntity stock = en.get().getProductStockEntity().iterator().next();
			if (stock.getQuantity() < 1) {
				throw new ValidationException("dataNotFoundCode", "noStockFoundForProduct", "successBooleanFalse");
			} else {
				int newStock = stock.getQuantity() - noOfProduct;
				stock.setQuantity(newStock);
				productStockRepository.save(stock);

				// Insert into Notification table
				productStockRepository.insertNotificationForProductStockUpdate(stock.getId().intValue(),
						Integer.parseInt(String.valueOf(Utility.getFCMProperty("stock.update.noti.type"))),
						String.valueOf(Utility.getFCMProperty("stock.update.noti.title")));
				
				// Push Notification
				pushNotification.pushNotification(
						Integer.parseInt(String.valueOf(Utility.getFCMProperty("stock.update.noti.type"))),
						String.valueOf(Utility.getFCMProperty("stock.update.noti.title")),en.get().getName());

			}
		} else {
			throw new ValidationException("dataNotFoundCode", "noStockFoundForProduct", "successBooleanFalse");
		}
	}

	private void deductFromPack(Integer noOfPacks, Integer packId, Optional<ProductEntity> en)
			throws ValidationException {
		// Get Pack Details
		Optional<ProductPackEntity> productPack = productPackRepository.findById((long) packId);
		ProductPackEntity packEntity = productPack.get();
		if (packEntity != null) {
			packEntity.setTotalPacksPerCase(packEntity.getTotalPacksPerCase() - noOfPacks);
			productPackRepository.save(packEntity);
			
			if(packEntity.getTotalPacksPerCase() < 1){
			// Insert into Notification table
			productStockRepository.insertNotificationForProductPackStockUpdate(packId,
					Integer.parseInt(String.valueOf(Utility.getFCMProperty("pack.stock.update.noti.type"))),
					String.valueOf(Utility.getFCMProperty("pack.stock.update.noti.title")));
			
			// Push Notification
			pushNotification.pushNotification(
					Integer.parseInt(String.valueOf(Utility.getFCMProperty("pack.stock.update.noti.type"))),
					String.valueOf(Utility.getFCMProperty("pack.stock.update.noti.title")),en.get().getName());
			
			}
			
		} else {
			throw new ValidationException("dataNotFoundCode", "noPackFoundForProduct", "successBooleanFalse");
		}

	}

}
