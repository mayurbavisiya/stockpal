package com.in.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in.entity.SizeLookupEntity;

@Repository
public interface SizeLookupRepository extends JpaRepository<SizeLookupEntity, Long> {

}
