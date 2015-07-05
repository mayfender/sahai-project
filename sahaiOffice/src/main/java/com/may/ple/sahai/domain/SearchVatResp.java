package com.may.ple.sahai.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SearchVatResp extends CommonResp {
	private List<Vat> vatLst;
	
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

}
