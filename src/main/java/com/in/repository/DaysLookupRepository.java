package com.in.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in.entity.DaysLookupEntity;

@Repository
public interface DaysLookupRepository extends JpaRepository<DaysLookupEntity, Long> {

}
