package com.in.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.ProductEntity;

@Repository
public interface InventoryRepository extends JpaRepository<ProductEntity, Long> {

	@Modifying
	@Transactional
	@Query(value = "update product_stock set quantity= quantity + :receivedQty where  product_id=:productId ", nativeQuery = true)
	void addProductStockByProductId(@Param("productId") Integer productId, @Param("receivedQty") Integer receivedQty);

	@Modifying
	@Transactional
	@Query(value = "update product_pack set total_packs_per_case =  total_packs_per_case + :receivedQty where  id =:packId and product_id=:productId", nativeQuery = true)
	void addProductPackByPackId(@Param("productId") Integer productId, @Param("packId") Integer packId,
			@Param("receivedQty") Integer receivedQty);

	
	@Modifying
	@Transactional
	@Query(value = "update product_stock set quantity= :receivedQty where  product_id=:productId ", nativeQuery = true)
	void updateProductStockByProductId(@Param("productId") Integer productId, @Param("receivedQty") Integer receivedQty);

	@Modifying
	@Transactional
	@Query(value = "update product_pack set total_packs_per_case =  :receivedQty where  id =:packId and product_id=:productId", nativeQuery = true)
	void updateProductPackByPackId(@Param("productId") Integer productId, @Param("packId") Integer packId,
			@Param("receivedQty") Integer receivedQty);

}
