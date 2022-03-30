package com.in.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.ProductEntity;

@Repository
public interface DashBoardRepository extends PagingAndSortingRepository<ProductEntity, Long> {

	@Query(value = "select sum(total) sales,sum(profit) net_profit,sum(total) transactions,(select count(*) from product_stock ps, product p where p.id = ps.product_id and p.is_active = 1 and ((:fromDate is null or :toDate is null) or (p.created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s')))) active_inventory,(select count(*) from product_stock ps,product p where p.id = ps.product_id and p.is_active = 1 and quantity < par_level and ((:fromDate is null or :toDate is null) or (p.created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s')))) low_Inventory, (select count(*) from product_stock ps,product p where  p.id = ps.product_id and p.is_active = 1 and quantity = 0 and ((:fromDate is null or :toDate is null) or (p.created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s')))) out_of_stock from  pos_order_items poi , product p where poi.product_id = p.id and  p.is_active = 1 and ((:fromDate is null or :toDate is null) or (poi.sale_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s')))", nativeQuery = true)
	List<Object[]> getDashBoardHeaderData(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Query(value = "select name,ps.quantity,pp.selling_price,p.id from product p ,product_stock ps, product_price pp  where  p.id = ps.product_id and  p.id = pp.product_id and  p.is_active = 1  and ((:fromDate is null or :toDate is null) or (p.created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) order by p.id desc  limit 5 ", nativeQuery = true)
	List<Object[]> getNewProducts(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Query(value = "select p.name,pp.old_selling_price,pp.selling_price new_selling_price,p.id  from product p,product_price pp where p.id = pp.product_id and p.is_active = 1  and  pp.price_update_date is not null and ((:fromDate is null or :toDate is null) or (p.created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) order by pp.price_update_date desc limit 5 ", nativeQuery = true)
	List<Object[]> getPriceChangeData(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Query(value = " select v.name,sum(poi.qty) qty_sold,sum(poi.total) total_sale,p.id from pos_order_items poi , product_price pp, product p, vendors v where p.id = poi.product_id and p.id = pp.product_id and v.id = pp.vendor_name and p.is_active=1  and ((:fromDate is null or :toDate is null) or (p.created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s')))  group by pp.vendor_name limit 5", nativeQuery = true)
	List<Object[]> getBestSeller(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Query(value = "select c.name,sum(poi.qty) qty_sold,sum(poi.total) total_sale,c.id from pos_order_items poi , product_price pp, product p, category c where p.id = poi.product_id and p.id = pp.product_id  and c.id = p.category and p.is_active=1  and ((:fromDate is null or :toDate is null) or (p.created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) group by pp.vendor_name limit 5", nativeQuery = true)
	List<Object[]> getBestCategory(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Query(value = "select hour(pos.sale_date) sale_hour,sum(pos.qty) sale_qty  from pos_order_items pos,product p, product_price pp where pos.product_id = p.id and p.id = pp.product_id and ((:fromDate is null or :toDate is null) or (sale_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) and (:vendorId is null or pp.vendor_name=:vendorId) group by hour(sale_date)", nativeQuery = true)
	List<Object[]> getSaleDataInGraph(@Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("vendorId") Integer vendorId);

	@Query(value = "select hour(sale_date) sale_hour,sum(total) total_transaction from pos_order_items  where ((:fromDate is null or :toDate is null) or (sale_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) group by hour(sale_date)", nativeQuery = true)
	List<Object[]> getTransactionDataInGraph(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

}
