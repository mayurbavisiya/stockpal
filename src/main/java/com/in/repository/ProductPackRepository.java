package com.in.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.ProductPackEntity;

@Repository
public interface ProductPackRepository extends PagingAndSortingRepository<ProductPackEntity, Long> {

	@Query(value = "SELECT pp.* FROM product_pack pp, product p where p.is_active=1 and pp.barcode=:barcodeNo ", nativeQuery = true)
	List<ProductPackEntity> getProductPackByBarCodeId(@Param("barcodeNo") String barcodeNo);

	@Query(value = "SELECT pp.* FROM product_pack pp,product p where p.is_active=1 and p.sku=:sku ", nativeQuery = true)
	List<ProductPackEntity> getProductPackBySKU(@Param("sku") String sku);

}
