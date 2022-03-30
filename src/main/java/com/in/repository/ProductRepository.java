package com.in.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.ProductEntity;
import com.in.entity.ProductPackEntity;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long> {
	@Query(value = "SELECT * FROM product where is_active=1 and lower(name) like %:name% order by id desc", nativeQuery = true)
	Page<ProductEntity> getProductByName(Pageable pageable, @Param("name") String name);

	@Query(value = "SELECT * FROM product where is_active=1 and lower(name) like %:name% order by id desc", nativeQuery = true)
	List<ProductEntity> getProductByName(@Param("name") String name);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM product_tags WHERE product_id = :productId ", nativeQuery = true)
	void deleteProductTagsByProductId(@Param("productId") Integer productId);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM product_pack WHERE product_id = :productId ", nativeQuery = true)
	void deleteProductPacksByProductId(@Param("productId") Integer productId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE product_price SET cost_price=:costPrice WHERE product_id = :productId ", nativeQuery = true)
	void updateProductPriceByProductId(@Param("productId") Integer productId, @Param("costPrice") Double costPrice);

	@Query(value = "SELECT * FROM product where is_active=1 and barcode=:barcodeNo ", nativeQuery = true)
	List<ProductEntity> getProductByBarCodeId(@Param("barcodeNo") String barcodeNo);

	@Query(value = "SELECT * FROM product where is_active=1 and sku=:sku ", nativeQuery = true)
	List<ProductEntity> getProductBySKU(@Param("sku") String sku);

//	@Query(value = "SELECT * FROM product_pack where p_p_id=:id ", nativeQuery = true)
//	ProductPackEntity getProductPackById(@Param("id") Integer id);
	
	@Query(value = "SELECT pp.* FROM product_pack pp, product p where p.is_active=1 and pp.p_p_id=:packId ", nativeQuery = true)
	List<ProductPackEntity> getProductPackById(@Param("packId") Long packId);


	@Modifying
	@Query(value = "UPDATE product SET category=:categoryId ", nativeQuery = true)
	void updateAllProductsCategory(@Param("categoryId") Integer categoryId);

	@Modifying
	@Query(value = "UPDATE product SET category=:categoryId where id in (:productIds)", nativeQuery = true)
	void updateAllProductsCategory(@Param("categoryId") Integer categoryId,
			@Param("productIds") ArrayList<Integer> productIdsList);

	@Query(value = "SELECT * FROM product where is_active=1 and category=:categoryId ", nativeQuery = true)
	List<ProductEntity> getAllProductBycategoryId(@Param("categoryId") Integer categoryId);

	@Query(value = "SELECT * FROM product where is_active=1 and lower(name) like %:name% ", nativeQuery = true)
	Page<ProductEntity> findAll(Pageable pageable, @Param("name") String name);

	@Query(value = "SELECT * FROM product p,product_stock ps where p.id=ps.product_id and (:activeInventory is null or p.is_active=1) and (:categoryId is  null or p.category=:categoryId) and (:name is null or lower(name) like %:name%) and ((:fromDate is null or :toDate is null) or (created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) and (:lowInventory is null or ps.quantity < ps.par_level) and (:outOfInventory is null or ps.quantity < 1) and (:disContinue is null or p.is_active=0)  order by id desc", nativeQuery = true)
	Page<ProductEntity> findAll(Pageable pageable, @Param("categoryId") Integer categoryId,
			@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("name") String name,
			@Param("activeInventory") String activeInventory, @Param("lowInventory") String lowInventory,
			@Param("outOfInventory") String outOfInventory, @Param("disContinue") String disContinue);

	@Query(value = "SELECT * FROM product where is_active=1 and id=:id ", nativeQuery = true)
	Optional<ProductEntity> findById(@Param("id") Long id);

	@Modifying
	@Transactional
	@Query(value = "UPDATE product_other_details SET promotions=NULL where promotions=:promotionId ", nativeQuery = true)
	void updatePromotionInProducts(@Param("promotionId") Integer promotionId);

	@Modifying
	@Query(value = "insert into notification(notification_type,title,notification_desc,created_date)values (:notificationType,:title,:desc,sysdate())", nativeQuery = true)
	void insertProductAddedNotification(@Param("notificationType") Integer notificationType,@Param("title") String title,@Param("desc") String desc);

}
