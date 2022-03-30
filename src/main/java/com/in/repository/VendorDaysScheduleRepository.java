package com.in.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.VendorDaysScheduleEntity;

@Repository
public interface VendorDaysScheduleRepository extends JpaRepository<VendorDaysScheduleEntity, Long> {

	@Modifying
	@Query(value = "DELETE FROM vendors_days_schedule WHERE vendor_id=:vendorId ", nativeQuery = true)
	void deleteMapping(@Param("vendorId") Long vendorId);

}
