package com.may.ple.sahai.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SearchVatResp extends CommonResp {
	private String vatDocNo;
	private String vatAddress;
	private String vatPayCondition;
	private String vatPayDate;
	private String vatPayNo;
	
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
