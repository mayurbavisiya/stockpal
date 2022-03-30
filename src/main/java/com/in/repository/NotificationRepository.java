package com.in.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.in.entity.NotificationEntity;

@Repository
public interface NotificationRepository extends PagingAndSortingRepository<NotificationEntity, Long> {

	@Query(value = "SELECT * FROM notification order by id desc", nativeQuery = true)
	Page<NotificationEntity> findAll(Pageable pageable);

}
