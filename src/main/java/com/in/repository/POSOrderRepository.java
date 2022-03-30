package com.in.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.in.entity.POSOrderEntity;

@Repository
public interface POSOrderRepository extends PagingAndSortingRepository<POSOrderEntity, Long> {

}
