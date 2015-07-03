package com.may.ple.sahai.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BuySaleJobResp {
	private String status;
	private List<BuySaleJobReq> searchLst;
	private Long totalItems;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<BuySaleJobReq> getSearchLst() {
		return searchLst;
	}

	public void setSearchLst(List<BuySaleJobReq> searchLst) {
		this.searchLst = searchLst;
	}

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

}
