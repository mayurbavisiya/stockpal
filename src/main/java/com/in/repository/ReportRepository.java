package com.in.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.POSOrderEntity;

@Repository
public interface ReportRepository extends PagingAndSortingRepository<POSOrderEntity, Long> {

	// Sales Report
	@Query(value = "select sum(total) total_sales,sum(qty) product_sold,sum(profit) net_profit from pos_order_items pos , product p, vendors v, product_price pp , category c where pos.product_id = p.id and p.category = c.id and p.id = pp.product_id and pp.vendor_name = v.id and ((:fromDate is null or :toDate is null) or (p.created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) and (:status is null or p.is_active=:status) and (:categoryId is null or c.id=:categoryId)  and (:vendorId is null or v.id=:vendorId)", nativeQuery = true)
	List<Object[]> getSalesReportHeaderValues(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("status") Integer status, @Param("categoryId") Integer categoryId,
			@Param("vendorId") Integer vendorId);

	@Query(value = "select * from salesreport where ((:fromDate is null or :toDate is null) or (created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) and (:status is null or is_active=:status) and (:categoryId is null or categoryId=:categoryId)  and (:vendorId is null or vendorId=:vendorId)", nativeQuery = true)
	Page<Object[]> getSalesReportData(Pageable pageable, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("status") Integer status, @Param("categoryId") Integer categoryId,
			@Param("vendorId") Integer vendorId);

	// Discount Report
	@Query(value = "select * from discount where ((:fromDate is null or :toDate is null) or (created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s')))", nativeQuery = true)
	Page<Object[]> getDiscountReportData(Pageable pageable, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate);

	// Vendor Sales Report
	@Query(value = "select sum(ps.quantity) inventory_stock,sum(ps.quantity)  * sum(pp.cost_price) inventory_value,sum(ps.quantity)  * (sum(pp.selling_price) - sum(pp.cost_price)) est_profit from product_stock ps, product_price pp, product p , category c , vendors v where ps.product_id = pp.product_id  and p.id = ps.product_id and p.category = c.id and pp.vendor_name = v.id and ((:fromDate is null or :toDate is null) or (p.created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) and (:status is null or p.is_active=:status) and (:categoryId is null or c.id=:categoryId)  and (:vendorId is null or v.id=:vendorId)", nativeQuery = true)
	List<Object[]> getVendorSalesReportHeaderValues(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("status") Integer status, @Param("categoryId") Integer categoryId,
			@Param("vendorId") Integer vendorId);

	@Query(value = "select * from vendorsalesdata where ((:fromDate is null or :toDate is null) or (created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) and (:status is null or is_active=:status) and (:categoryId is null or categoryId=:categoryId)  and (:vendorId is null or vendorId=:vendorId)", nativeQuery = true)
	Page<Object[]> getVendorSalesReportData(Pageable pageable, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("status") Integer status, @Param("categoryId") Integer categoryId,
			@Param("vendorId") Integer vendorId);

	// Dead Inventory Report
	@Query(value = "select p.id,sum(ps.quantity) inventoryStock,sum(pp.cost_price) inventoryValue,sum(ps.quantity)  * (sum(pp.selling_price) - sum(pp.cost_price)) est_profit from product_other_details po, product p, product_stock ps, product_price pp, category c, vendors v where  po.product_id = p.id and p.id = ps.product_id and p.category = c.id and pp.vendor_name = v.id and po.promotion_expiry_date is not null and promotion_expiry_date < sysdate() and ((:fromDate is null or :toDate is null) or (p.created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) and (:status is null or p.is_active=:status) and (:categoryId is null or p.category=:categoryId)  and (:vendorId is null or pp.vendor_name=:vendorId)", nativeQuery = true)
	List<Object[]> getDeadInventoryReportHeaderValues(@Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("status") Integer status, @Param("categoryId") Integer categoryId,
			@Param("vendorId") Integer vendorId);

	@Query(value = "select * from deadinventory where ((:fromDate is null or :toDate is null) or (created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) and (:status is null or is_active=:status) and (:categoryId is null or categoryId=:categoryId)  and (:vendorId is null or vendorId=:vendorId)", nativeQuery = true)
	Page<Object[]> getDeadInventoryReportData(Pageable pageable, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("status") Integer status, @Param("categoryId") Integer categoryId,
			@Param("vendorId") Integer vendorId);

	// Low Inventory Report
	@Query(value = "select p.id,sum(ps.quantity) inventoryStock,sum(pp.cost_price) inventoryValue,sum(ps.quantity)  * (sum(pp.selling_price) - sum(pp.cost_price)) est_profit from product_other_details po, product p, product_stock ps, product_price pp,category c, vendors v where po.product_id = p.id and p.id = ps.product_id and c.id = p.category and v.id = pp.vendor_name and ((:fromDate is null or :toDate is null) or (p.created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) and (:status is null or p.is_active=:status) and (:categoryId is null or p.category=:categoryId)  and (:vendorId is null or pp.vendor_name=:vendorId)", nativeQuery = true)
	List<Object[]> getLowInventoryReportHeaderValues(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("status") Integer status, @Param("categoryId") Integer categoryId,
			@Param("vendorId") Integer vendorId);

	@Query(value = "select * from lowinventory where ((:fromDate is null or :toDate is null) or (created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) and (:status is null or is_active=:status) and (:categoryId is null or categoryId=:categoryId)  and (:vendorId is null or vendorId=:vendorId)", nativeQuery = true)
	Page<Object[]> getLowInventoryReportData(Pageable pageable, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("status") Integer status, @Param("categoryId") Integer categoryId,
			@Param("vendorId") Integer vendorId);

	// Stock ForeCast Report
	@Query(value = "select * from stockforecast where (:vendorId is null or vendorId=:vendorId)", nativeQuery = true)
	Page<Object[]> getStockForeCastPage(Pageable pageable, @Param("vendorId") Integer vendorId);

}
