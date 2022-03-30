package com.in.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Long> {

	@Query(value = "SELECT * FROM category where is_active = 1 and lower(name)=lower(:name) ", nativeQuery = true)
	List<CategoryEntity> getCategoryByName(@Param("name") String name);

	@Query(value = "select c.id,c.name cat_name,c.margin_percentage,c.ebt_enabled,c.description,date_format(c.created_date,'%d/%m/%Y') createDate,date_format(c.updated_date,'%d/%m/%Y') updateDate,c.parent_category_id,c.parent_category_name,p.id product_id,p.name productName from category c left join product p on c.id = p.category where c.is_active = 1 and c.id=:categoryId", nativeQuery = true)
	List<Object[]> getCategoryDetailsWithProduct(@Param("categoryId") Integer categoryId);

	@Query(value = "SELECT * FROM category where is_active = 1 and (:name is null or lower(name) like %:name%) and ((:fromDate is null or :toDate is null) or (created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) order by id desc", nativeQuery = true)
	Page<CategoryEntity> findAll(Pageable pageable, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("name") String name);

	@Query(value = "SELECT * FROM category where is_active = 1 and id=:id ", nativeQuery = true)
	Optional<CategoryEntity> findById(@Param("id") Long id);
}
