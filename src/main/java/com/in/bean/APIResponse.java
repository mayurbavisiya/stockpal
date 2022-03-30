package com.in.bean;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.in.exception.ExceptionUtility;
import com.in.response.dto.CategoryResDTO;
import com.in.response.dto.DashBoardGraphSaleDataResDTO;
import com.in.response.dto.DashBoardResDTO;
import com.in.response.dto.DaysLookupDTO;
import com.in.response.dto.EBTLookupResDTO;
import com.in.response.dto.ForgotPasswordResDTO;
import com.in.response.dto.InvoiceResDTO;
import com.in.response.dto.JwtResponse;
import com.in.response.dto.NotificationResDTO;
import com.in.response.dto.OrderDetailsByOrderIdResDTO;
import com.in.response.dto.OrderProductByVendorsResDTO;
import com.in.response.dto.OrderResDTO;
import com.in.response.dto.PriceTypesResDTO;
import com.in.response.dto.ProductResDTO;
import com.in.response.dto.ProductStockAdjustmentResDTO;
import com.in.response.dto.PromotionResDTO;
import com.in.response.dto.QuantityLookupResDTO;
import com.in.response.dto.ReportResDTO;
import com.in.response.dto.SizeLookupResDTO;
import com.in.response.dto.TaxResDTO;
import com.in.response.dto.TaxTypesLookupResDTO;
import com.in.response.dto.UpcomingScheduledOrderResDTO;
import com.in.response.dto.UserResDTO;
import com.in.response.dto.VendorResDTO;

public class APIResponse {

	private String responseCode;
	private String responseMsg;
	private boolean success;

	@JsonInclude(Include.NON_NULL)
	private List<VendorResDTO> vendors;

	@JsonInclude(Include.NON_NULL)
	private List<DaysLookupDTO> days;

	@JsonInclude(Include.NON_NULL)
	private Integer totalPages;

	@JsonInclude(Include.NON_NULL)
	private Integer totalRecords;

	@JsonInclude(Include.NON_NULL)
	private Integer pageRecords;

	@JsonInclude(Include.NON_NULL)
	private JwtResponse jwtDetails;

	@JsonInclude(Include.NON_NULL)
	private List<TaxResDTO> tax;

	@JsonInclude(Include.NON_NULL)
	private List<TaxTypesLookupResDTO> taxTypes;

	@JsonInclude(Include.NON_NULL)
	private List<CategoryResDTO> categories;

	@JsonInclude(Include.NON_NULL)
	private List<SizeLookupResDTO> sizes;

	@JsonInclude(Include.NON_NULL)
	private List<PriceTypesResDTO> priceTypes;

	@JsonInclude(Include.NON_NULL)
	private List<QuantityLookupResDTO> qtyList;

	@JsonInclude(Include.NON_NULL)
	private List<EBTLookupResDTO> ebtTypes;

	@JsonInclude(Include.NON_NULL)
	private ProductResDTO product;

	@JsonInclude(Include.NON_NULL)
	private List<ProductResDTO> products;

	@JsonInclude(Include.NON_NULL)
	private String commonMessage;

	@JsonInclude(Include.NON_NULL)
	private String productImageS3Url;

	@JsonInclude(Include.NON_NULL)
	private List<PromotionResDTO> promotions;

	@JsonInclude(Include.NON_NULL)
	private PromotionResDTO promotion;

	@JsonInclude(Include.NON_NULL)
	private List<InvoiceResDTO> invoices;

	@JsonInclude(Include.NON_NULL)
	private List<OrderResDTO> orders;

	@JsonInclude(Include.NON_NULL)
	private OrderResDTO order;

	@JsonInclude(Include.NON_NULL)
	private HashMap<String, List<UpcomingScheduledOrderResDTO>> scheduledOrders;

	@JsonInclude(Include.NON_NULL)
	private List<NotificationResDTO> notifications;

	@JsonInclude(Include.NON_NULL)
	private List<UserResDTO> users;

	@JsonInclude(Include.NON_NULL)
	private UserResDTO user;

	@JsonInclude(Include.NON_NULL)
	private ReportResDTO report;

	@JsonInclude(Include.NON_NULL)
	private List<OrderProductByVendorsResDTO> productByVendors;

	@JsonInclude(Include.NON_NULL)
	private List<OrderDetailsByOrderIdResDTO> orderDetailsByOrderId;

	@JsonInclude(Include.NON_NULL)
	private List<ProductStockAdjustmentResDTO> stockAdjustment;

	@JsonInclude(Include.NON_NULL)
	private DashBoardResDTO dashBoardResponse;

	@JsonInclude(Include.NON_NULL)
	ForgotPasswordResDTO forgotPassword;

	@JsonInclude(Include.NON_NULL)
	DashBoardGraphSaleDataResDTO graphData;

	public APIResponse(String responseCode, String responseMsg, String success) {
		this.responseCode = ExceptionUtility.getCode(responseCode);
		this.responseMsg = ExceptionUtility.getCode(responseMsg);
		this.success = (ExceptionUtility.getCode(success).trim().equals("true")) ? true : false;

	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<VendorResDTO> getVendors() {
		return vendors;
	}

	public void setVendors(List<VendorResDTO> vendors) {
		this.vendors = vendors;
	}

	public List<DaysLookupDTO> getDays() {
		return days;
	}

	public void setDays(List<DaysLookupDTO> days) {
		this.days = days;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getPageRecords() {
		return pageRecords;
	}

	public void setPageRecords(Integer pageRecords) {
		this.pageRecords = pageRecords;
	}

	public JwtResponse getJwtDetails() {
		return jwtDetails;
	}

	public void setJwtDetails(JwtResponse jwtDetails) {
		this.jwtDetails = jwtDetails;
	}

	public List<TaxResDTO> getTax() {
		return tax;
	}

	public void setTax(List<TaxResDTO> tax) {
		this.tax = tax;
	}

	public List<TaxTypesLookupResDTO> getTaxTypes() {
		return taxTypes;
	}

	public void setTaxTypes(List<TaxTypesLookupResDTO> taxTypes) {
		this.taxTypes = taxTypes;
	}

	public List<CategoryResDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryResDTO> categories) {
		this.categories = categories;
	}

	public List<SizeLookupResDTO> getSizes() {
		return sizes;
	}

	public void setSizes(List<SizeLookupResDTO> sizes) {
		this.sizes = sizes;
	}

	public List<PriceTypesResDTO> getPriceTypes() {
		return priceTypes;
	}

	public void setPriceTypes(List<PriceTypesResDTO> priceTypes) {
		this.priceTypes = priceTypes;
	}

	public List<QuantityLookupResDTO> getQtyList() {
		return qtyList;
	}

	public void setQtyList(List<QuantityLookupResDTO> qtyList) {
		this.qtyList = qtyList;
	}

	public List<EBTLookupResDTO> getEbtTypes() {
		return ebtTypes;
	}

	public void setEbtTypes(List<EBTLookupResDTO> ebtTypes) {
		this.ebtTypes = ebtTypes;
	}

	public List<ProductResDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductResDTO> products) {
		this.products = products;
	}

	public String getProductImageS3Url() {
		return productImageS3Url;
	}

	public void setProductImageS3Url(String productImageS3Url) {
		this.productImageS3Url = productImageS3Url;
	}

	public List<PromotionResDTO> getPromotions() {
		return promotions;
	}

	public void setPromotions(List<PromotionResDTO> promotions) {
		this.promotions = promotions;
	}

	public List<InvoiceResDTO> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<InvoiceResDTO> invoices) {
		this.invoices = invoices;
	}

	public ProductResDTO getProduct() {
		return product;
	}

	public void setProduct(ProductResDTO product) {
		this.product = product;
	}

	public List<OrderResDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderResDTO> orders) {
		this.orders = orders;
	}

	public HashMap<String, List<UpcomingScheduledOrderResDTO>> getScheduledOrders() {
		return scheduledOrders;
	}

	public void setScheduledOrders(HashMap<String, List<UpcomingScheduledOrderResDTO>> scheduledOrders) {
		this.scheduledOrders = scheduledOrders;
	}

	public List<NotificationResDTO> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<NotificationResDTO> notifications) {
		this.notifications = notifications;
	}

	public PromotionResDTO getPromotion() {
		return promotion;
	}

	public void setPromotion(PromotionResDTO promotion) {
		this.promotion = promotion;
	}

	public List<UserResDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserResDTO> users) {
		this.users = users;
	}

	public UserResDTO getUser() {
		return user;
	}

	public void setUser(UserResDTO user) {
		this.user = user;
	}

	public String getCommonMessage() {
		return commonMessage;
	}

	public void setCommonMessage(String commonMessage) {
		this.commonMessage = commonMessage;
	}

	public OrderResDTO getOrder() {
		return order;
	}

	public void setOrder(OrderResDTO order) {
		this.order = order;
	}

	public ReportResDTO getReport() {
		return report;
	}

	public void setReport(ReportResDTO report) {
		this.report = report;
	}

	public List<OrderProductByVendorsResDTO> getProductByVendors() {
		return productByVendors;
	}

	public void setProductByVendors(List<OrderProductByVendorsResDTO> productByVendors) {
		this.productByVendors = productByVendors;
	}

	public List<OrderDetailsByOrderIdResDTO> getOrderDetailsByOrderId() {
		return orderDetailsByOrderId;
	}

	public void setOrderDetailsByOrderId(List<OrderDetailsByOrderIdResDTO> orderDetailsByOrderId) {
		this.orderDetailsByOrderId = orderDetailsByOrderId;
	}

	public List<ProductStockAdjustmentResDTO> getStockAdjustment() {
		return stockAdjustment;
	}

	public void setStockAdjustment(List<ProductStockAdjustmentResDTO> stockAdjustment) {
		this.stockAdjustment = stockAdjustment;
	}

	public DashBoardResDTO getDashBoardResponse() {
		return dashBoardResponse;
	}

	public void setDashBoardResponse(DashBoardResDTO dashBoardResponse) {
		this.dashBoardResponse = dashBoardResponse;
	}

	public ForgotPasswordResDTO getForgotPassword() {
		return forgotPassword;
	}

	public void setForgotPassword(ForgotPasswordResDTO forgotPassword) {
		this.forgotPassword = forgotPassword;
	}

	public DashBoardGraphSaleDataResDTO getGraphData() {
		return graphData;
	}

	public void setGraphData(DashBoardGraphSaleDataResDTO graphData) {
		this.graphData = graphData;
	}

}
