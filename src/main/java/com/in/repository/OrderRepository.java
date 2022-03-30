package com.in.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.OrderEntity;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, Long> {

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM order_product WHERE order_id = :orderId ", nativeQuery = true)
	void deleteOrderProductsByOrderId(@Param("orderId") Integer orderId);

	@Query(value = "select GROUP_CONCAT(p.name SEPARATOR ', ') product_name from order_product op, product p where  p.is_active = 1 and op.product_id  = p.id and op.order_id = :orderId ", nativeQuery = true)
	List<Object[]> getProductsByOrderId(@Param("orderId") Integer orderId);

	@Query(value = "select * from order_ where vendor_id=:vendorId order by id desc ", nativeQuery = true)
	Page<OrderEntity> getOrdersByVendor(Pageable pageable, @Param("vendorId") Integer vendorId);

	@Query(value = "select p.id,p.name productName,DATE_FORMAT(max(o.order_date), '%d-%m-%Y %T') lastOrder,ps.reorder_quantity,(select sum(qty) from pos_order_items where  sale_date BETWEEN CURDATE() - INTERVAL 30 DAY AND CURDATE() and product_id = p.id) qtysold30days,(select sum(qty) from pos_order_items where  sale_date BETWEEN CURDATE() - INTERVAL 7 DAY AND CURDATE() and product_id = p.id) qtysold7days, sum(ps.quantity) in_stock_qty, (select reorder_qty from order_product where product_id = p.id order by o_p_id limit 1) re_order_qty, (select sgsted_reorder_qty from order_product where product_id = p.id order by o_p_id limit 1) suggestedorder_qty,pp.cost_price,pp.margin,pp.selling_price from product p inner join product_price pp on pp.product_id = p.id inner join product_stock ps on ps.product_id = p.id inner join vendors v on pp.vendor_name = v.id left join order_product op on p.id = op.product_id left join order_ o on op.order_id = o.id where  p.is_active=1 and  v.id =:vendorId group by  p.id,p.name  order by p.id desc", nativeQuery = true)
	List<Object[]> getAllProductsByVendor(@Param("vendorId") Integer vendorId);

	@Query(value = "select o.id order_id,o.vendor_id,v.name vendor_name,v.CONTACT_PERSON_NAME vendor_contact_name,v.mobile vendor_mobile ,v.ADDRESS vendor_address,v.email vendor_email, v.FAX vendor_fax,op.product_id,p.name product_name,op.reorder_qty qty,DATE_FORMAT(o.order_date, '%d-%m-%Y %T') order_date,pp.cost_price,pp.margin,pp.selling_price from order_ o left join order_product op on o.id = op.order_id left join product p on p.id = op.product_id left join vendors v on v.id = o.vendor_id left join product_price pp on p.id=pp.product_id  where p.is_active=1 and o.id=:orderId order by o.id desc ", nativeQuery = true)
	List<Object[]> getOrderDetailsByOrderId(@Param("orderId") Integer orderId);

	@Query(value = "select * from order_ order by id desc ", nativeQuery = true)
	Page<OrderEntity> findAll(Pageable pageable);

	@Query(value = "select o.id order_id,o.vendor_id,date_format(o.order_date,'%d/%m/%Y') order_date,op.o_p_id op_id,op.product_id,op.reorder_qty,op.sgsted_reorder_qty,p.name product_name from order_ o, order_product op,product p where o.id=op.order_id and p.id = op.product_id and p.is_active = 1 and o.id =:orderId", nativeQuery = true)
	List<Object[]> getOrderByOrderId(@Param("orderId") Long orderId);

	@Query(value = "select * from(select concat(t.product_name) notidesc,vendor_name from(select  GROUP_CONCAT(p.name SEPARATOR '-') product_name , v.name  vendor_name from days_lookup dl inner join (select DAYNAME(sysdate()) day from dual) as d on lower(day_name) = lower(day) inner join vendors_days_schedule vds on dl.id = vds.day_id inner join  vendors v on v.id = vds.vendor_id inner join product_price pp on  v.id = pp.vendor_name inner join product p on p.id = pp.product_id group by vendor_id,vendor_name)t)a", nativeQuery = true)
	List<Object[]> getOrderDetailsTobePlacedtoVendor();

}
