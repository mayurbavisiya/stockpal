package com.in.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.TaxEntity;

@Repository
public interface TaxRepository extends PagingAndSortingRepository<TaxEntity, Long> {

	@Query(value = "SELECT * FROM tax where lower(ratename)=lower(:ratename) ", nativeQuery = true)
	List<TaxEntity> getTaxByName(@Param("ratename") String ratename);

	@Query(value = "SELECT * FROM tax where flag=1 order by id desc ", nativeQuery = true)
	Page<TaxEntity> findAll(Pageable pageable);

	@Query(value = "SELECT * FROM tax where flag=1 and id=:id order by id desc ", nativeQuery = true)
	Optional<TaxEntity> findById(@Param("id") Long id);

}
