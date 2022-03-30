package com.in.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.ProductStockEntity;

@Repository
public interface ProductStockRepository extends PagingAndSortingRepository<ProductStockEntity, Long> {

	@Transactional
	@Modifying
	@Query(value = "insert into notification(notification_type,title,notification_desc,created_date)    select :notificationType,:title, p.name ,sysdate() from product_stock ps , product p where ps.product_id = p.id and  quantity  <= par_level and ps.ps_id=:id", nativeQuery = true)
	void insertNotificationForProductStockUpdate(@Param("id") Integer id, @Param("notificationType") Integer notificationType,
			@Param("title") String title);
	
	@Transactional
	@Modifying
	@Query(value = "insert into notification(notification_type,title,notification_desc,created_date)    select :notificationType,:title, p.name ,sysdate() from product_pack p where p.p_p_id=:packId", nativeQuery = true)
	void insertNotificationForProductPackStockUpdate(@Param("packId") Integer packId, @Param("notificationType") Integer notificationType,
			@Param("title") String title);
}
