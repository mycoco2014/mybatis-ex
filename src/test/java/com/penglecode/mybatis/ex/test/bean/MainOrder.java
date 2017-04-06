package com.penglecode.mybatis.ex.test.bean;
/**
 * 订单主表
 * 
 * @author	  	pengpeng
 * @date	  	2014年7月8日 下午11:19:51
 * @version  	1.0
 */
public class MainOrder {

	private Long orderId;
	
	private Long userId;
	
	private Double totalMoney;
	
	private Double discount;
	
	private Double totalFreight;
	
	private String orderTime;
	
	private String status;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(Double totalFreight) {
		this.totalFreight = totalFreight;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
