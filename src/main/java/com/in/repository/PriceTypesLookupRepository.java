package com.in.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in.entity.PriceTypesLookupEntity;

@Repository
public interface PriceTypesLookupRepository extends JpaRepository<PriceTypesLookupEntity, Long> {

}
