package com.in.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.PromotionEntity;

@Repository
public interface PromotionRepository extends PagingAndSortingRepository<PromotionEntity, Long> {

	@Query(value = "SELECT * FROM promotions where lower(name)=lower(:name) order by id desc ", nativeQuery = true)
	List<PromotionEntity> getPromotionByName(@Param("name") String name);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM promotion_type WHERE promotion_id = :promotionId ", nativeQuery = true)
	void deletePromotionTypeByPromotionId(@Param("promotionId") Integer promotionId);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM promotion_type_time_period WHERE promotion_id = :promotionId ", nativeQuery = true)
	void deletePromotionTypeTimePeriodByPromotionId(@Param("promotionId") Integer promotionId);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM promotion_type_product_map WHERE promotion_id = :promotionId ", nativeQuery = true)
	void deletePromotionTypeProductMapByPromotionId(@Param("promotionId") Integer promotionId);

	@Query(value = "SELECT * FROM promotions where  (:name is null or lower(name) like %:name%) order by id desc", nativeQuery = true)
	Page<PromotionEntity> findAll(Pageable pageable, @Param("name") String name);

}
