package com.in.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.entity.InvoiceEntity;

@Repository
public interface InvoiceRepository extends PagingAndSortingRepository<InvoiceEntity, Long> {

	@Query(value = "SELECT * FROM invoice where is_active=1 and invoice_number = :invoiceNumber ", nativeQuery = true)
	List<InvoiceEntity> getInvoiceByNumber(@Param("invoiceNumber") String invoiceNumber);

	@Query(value = "SELECT * FROM invoice where is_active=1 ", nativeQuery = true)
	Page<InvoiceEntity> findAll(Pageable pageable);

	@Query(value = "SELECT * FROM invoice where is_active=1 and id=:id ", nativeQuery = true)
	Optional<InvoiceEntity> findById(@Param("id") Long id);

	@Query(value = "select i.id,i.invoice_number,i.vendor_id,v.name vendor_name,i.order_id,date_format(i.invoice_date,'%d/%m/%Y') invoice_date,date_format(i.due_date,'%d/%m/%Y') due_date,i.invoice_photo_url,ip.product_id,p.name product_name,ip.received_qty,ip.cost_price,ip.margin,ip.selling_price from invoice i,invoice_product ip,product p,vendors v where i.id = ip.invoice_id and ip.product_id = p.id and i.vendor_id = v.id and i.is_active = 1 and i.id=:id ", nativeQuery = true)
	List<Object[]> findInvoiceById(@Param("id") Long id);

	@Query(value = "SELECT * FROM invoice where is_active=1 and (:invoiceId is null or invoice_number=:invoiceId) and ((:fromDate is null or :toDate is null) or (created_date between STR_TO_DATE(:fromDate, '%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:toDate, '%d/%m/%Y %H:%i:%s'))) order by id desc ", nativeQuery = true)
	Page<InvoiceEntity> findAll(Pageable pageable, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("invoiceId") String invoiceId);
}
