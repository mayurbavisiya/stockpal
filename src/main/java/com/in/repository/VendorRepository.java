package com.in.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.VendorEntity;

@Repository
public interface VendorRepository extends PagingAndSortingRepository<VendorEntity, Long> {

	@Query(value = "SELECT * FROM vendors where is_active=1 and lower(name) like (concat('%', :name,'%')) order by id desc ", nativeQuery = true)
	List<VendorEntity> getVendorContainsName(@Param("name") String vendorName);

	@Query(value = "SELECT * FROM vendors where is_active=1 and lower(name) = lower(:name) order by id desc ", nativeQuery = true)
	List<VendorEntity> getVendorByName(@Param("name") String vendorName);

	@Query(value = "select dl.day_name,vs.vendor_id,v.name,v.mobile,v.fax,v.email from vendors_days_schedule vs , days_lookup dl,vendors v where v.is_active=1 and dl.id = vs.day_id and v.id = vs.vendor_id order by dl.id", nativeQuery = true)
	List<Object[]> getScheduledVendors();

	@Query(value = "select * from vendors where is_active=1 and  (:name is null or lower(name) like %:name%) order by id desc", nativeQuery = true)
	Page<VendorEntity> findAll(Pageable pageable, @Param("name") String name);

	@Query(value = "SELECT * FROM vendors where is_active=1 and id=:id ", nativeQuery = true)
	Optional<VendorEntity> findById(@Param("id") Long id);
}
