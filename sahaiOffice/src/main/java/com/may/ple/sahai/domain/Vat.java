package com.may.ple.sahai.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Vat {
	private String vatDocNo;
	private String vatAddress;
	private String vatPayCondition;
	private String vatPayDate;
	private String vatDueDate;
	private String vatPayNo;
	private String companyName;
	
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

	public String getVatPayDate() {
		return vatPayDate;
	}

	public void setVatPayDate(String vatPayDate) {
		this.vatPayDate = vatPayDate;
	}

	public String getVatPayNo() {
		return vatPayNo;
	}

	public void setVatPayNo(String vatPayNo) {
		this.vatPayNo = vatPayNo;
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
	
}
