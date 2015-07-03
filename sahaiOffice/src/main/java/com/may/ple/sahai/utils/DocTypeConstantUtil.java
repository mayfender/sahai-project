package com.may.ple.sahai.utils;

public enum DocTypeConstantUtil {
	UNDEFINED(-1), PURCHASE_ORDER(1), QUOTATION(2), QUOTATION_REQUEST(3);
	
	private Integer type;
	
	private DocTypeConstantUtil(Integer type) {
		this.type = type;
	}
	
	public static DocTypeConstantUtil parseType(Integer type) {
		if(QUOTATION.type.equals(type)) {
			return QUOTATION;
		} else if(QUOTATION_REQUEST.type.equals(type)) {
			return QUOTATION_REQUEST;			
		} else if(PURCHASE_ORDER.type.equals(type)) {
			return PURCHASE_ORDER;						
		} else {
			return UNDEFINED;									
		}
	}

	public Integer getType() {
		return type;
	}
	
}
