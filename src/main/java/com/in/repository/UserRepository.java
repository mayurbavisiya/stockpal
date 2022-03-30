package com.in.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.in.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByUsername(String username);

	@Query(value = "SELECT * FROM user order by id desc", nativeQuery = true)
	Page<UserEntity> findAll(Pageable pageable);
}
