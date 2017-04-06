package com.penglecode.mybatis.ex.test.bean;

public class Product {

	private Long productId;
	
	private String productName;
	
	private Double unitPrice;
	
	private Double freight;
	
	private String productType;
	
	private String description;
	
	private String status;
	
	private String createTime;
	
	private String updateTime;

	public Product() {
		super();
	}

	public Product(Long productId, String productName, Double unitPrice,
			Double freight, String productType, String description,
			String status, String createTime, String updateTime) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.freight = freight;
		this.productType = productType;
		this.description = description;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
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

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
