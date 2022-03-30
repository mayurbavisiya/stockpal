package com.in.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.ProductStockAdjustementEntity;

@Repository
public interface ProductStockAdjustmentRepository
		extends PagingAndSortingRepository<ProductStockAdjustementEntity, Long> {

	@Query(value = "select * from product_stock_adjustment where product_id=:id", nativeQuery = true)
	Page<ProductStockAdjustementEntity> findAll(Pageable pageable, @Param("id") Long id);

}
