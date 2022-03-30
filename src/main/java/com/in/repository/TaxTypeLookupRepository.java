package com.in.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.TaxTypesLookupEntity;

@Repository
public interface TaxTypeLookupRepository extends JpaRepository<TaxTypesLookupEntity, Long> {

	@Query(value = "SELECT * FROM tax_type_lookup where id =:id ", nativeQuery = true)
	TaxTypesLookupEntity findById(@Param("id") Integer id);

}
	