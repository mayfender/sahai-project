package com.may.ple.sahai.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BuySaleTaskReq extends CommonResp {
	private String id;
	private String jobId;
	private String createdDateTime;
	private String docNo;
	private String docType;
	private String companyName;
	private String address;
	private String contactPersonName;
	private String contactPersonTel;
	private String contactPersonFax;
	private String userName;
	private String userTel;
	private String comments;
	private String payCondition;
	private String placeToSend;
	private String dateToSend;
	private Double firstPrice;
	private Double discount;
	private Double afterDiscount;
	private Double vat;
	private Double totalPrice;
	private List<ItemInfo> items;
	private VatSaveReq vatObj;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getContactPersonTel() {
		return contactPersonTel;
	}

	public void setContactPersonTel(String contactPersonTel) {
		this.contactPersonTel = contactPersonTel;
	}

	public String getContactPersonFax() {
		return contactPersonFax;
	}

	public void setContactPersonFax(String contactPersonFax) {
		this.contactPersonFax = contactPersonFax;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Double getFirstPrice() {
		return firstPrice;
	}

	public void setFirstPrice(Double firstPrice) {
		this.firstPrice = firstPrice;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getAfterDiscount() {
		return afterDiscount;
	}

	public void setAfterDiscount(Double afterDiscount) {
		this.afterDiscount = afterDiscount;
	}

	public Double getVat() {
		return vat;
	}

	public void setVat(Double vat) {
		this.vat = vat;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<ItemInfo> getItems() {
		if(items == null) items = new ArrayList<ItemInfo>();
		return items;
	}

	public void setItems(List<ItemInfo> items) {
		this.items = items;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getPayCondition() {
		return payCondition;
	}

	public void setPayCondition(String payCondition) {
		this.payCondition = payCondition;
	}

	public String getPlaceToSend() {
		return placeToSend;
	}

	public void setPlaceToSend(String placeToSend) {
		this.placeToSend = placeToSend;
	}

	public String getDateToSend() {
		return dateToSend;
	}

	public void setDateToSend(String dateToSend) {
		this.dateToSend = dateToSend;
	}

	public VatSaveReq getVatObj() {
		return vatObj;
	}

	public void setVatObj(VatSaveReq vatObj) {
		this.vatObj = vatObj;
	}
	
}
