package com.may.ple.sahai.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SearchVatResp extends CommonResp {
	private List<Vat> vatLst;
	private Long totalItems;
	private String sumVatInTotalPrice;
	private String sumVatOutTotalPrice;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	public List<Vat> getVatLst() {
		return vatLst;
	}

	public void setVatLst(List<Vat> vatLst) {
		this.vatLst = vatLst;
	}

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public String getSumVatInTotalPrice() {
		return sumVatInTotalPrice;
	}

	public void setSumVatInTotalPrice(String sumVatInTotalPrice) {
		this.sumVatInTotalPrice = sumVatInTotalPrice;
	}

	public String getSumVatOutTotalPrice() {
		return sumVatOutTotalPrice;
	}

	public void setSumVatOutTotalPrice(String sumVatOutTotalPrice) {
		this.sumVatOutTotalPrice = sumVatOutTotalPrice;
	}

}
