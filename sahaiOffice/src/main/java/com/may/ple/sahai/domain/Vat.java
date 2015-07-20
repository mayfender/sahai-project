package com.may.ple.sahai.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Vat extends CommonResp {
	private String id;
	private String vatDocNo;
	private String vatAddress;
	private String vatPayCondition;
	private String vatCreatedDateTime;
	private String vatDueDate;
	private String vatPoNo;
	private String companyName;
	private String totalPrice;
	private String others;
	private String userName;
	private String vatType;
	private String taskId;
	private String releaseVatDate;
	
	public String getReleaseVatDate() {
		return releaseVatDate;
	}

	public void setReleaseVatDate(String releaseVatDate) {
		this.releaseVatDate = releaseVatDate;
	}

	private Boolean isCreatedVat;
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getVatPoNo() {
		return vatPoNo;
	}

	public void setVatPoNo(String vatPoNo) {
		this.vatPoNo = vatPoNo;
	}

	public String getVatType() {
		return vatType;
	}

	public void setVatType(String vatType) {
		this.vatType = vatType;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	public String getVatDocNo() {
		return vatDocNo;
	}

	public void setVatDocNo(String vatDocNo) {
		this.vatDocNo = vatDocNo;
	}

	public String getVatAddress() {
		return vatAddress;
	}

	public void setVatAddress(String vatAddress) {
		this.vatAddress = vatAddress;
	}

	public String getVatPayCondition() {
		return vatPayCondition;
	}

	public void setVatPayCondition(String vatPayCondition) {
		this.vatPayCondition = vatPayCondition;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getVatDueDate() {
		return vatDueDate;
	}

	public void setVatDueDate(String vatDueDate) {
		this.vatDueDate = vatDueDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVatCreatedDateTime() {
		return vatCreatedDateTime;
	}

	public void setVatCreatedDateTime(String vatCreatedDateTime) {
		this.vatCreatedDateTime = vatCreatedDateTime;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsCreatedVat() {
		return isCreatedVat;
	}

	public void setIsCreatedVat(Boolean isCreatedVat) {
		this.isCreatedVat = isCreatedVat;
	}
	
}
