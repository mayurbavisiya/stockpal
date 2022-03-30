package com.in.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.ForgotPasswordEntity;

@Repository
public interface ForgotPasswordRepository extends PagingAndSortingRepository<ForgotPasswordEntity, Long> {

	@Query(value = "SELECT * FROM forgot_password where lower(token) =:token ", nativeQuery = true)
	ForgotPasswordEntity findDataByToken(@Param("token") String token);

	@Modifying
	@Query(value = "update forgot_password set token_expired=1 where username=:userName and token_expired=0", nativeQuery = true)
	void updateAllPreviousToken(@Param("userName") String userName);
}
