package com.penglecode.mybatis.ex.test.bean;
/**
 * 订单子表
 * 
 * @author	  	pengpeng
 * @date	  	2014年7月8日 下午11:20:10
 * @version  	1.0
 */
public class ChildOrder {

	private Long orderId;
	
	private Long mainOrderId;
	
	private Long productId;
	
	private String productName;
	
	private Double unitPrice;
	
	private Integer buyNum;
	
	private Double freight;
	
	private Double subTotal;
	
	private String orderTime;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getMainOrderId() {
		return mainOrderId;
	}

	public void setMainOrderId(Long mainOrderId) {
		this.mainOrderId = mainOrderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	
}
