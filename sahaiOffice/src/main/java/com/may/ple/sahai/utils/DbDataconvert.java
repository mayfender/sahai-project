package com.may.ple.sahai.utils;

public class DbDataconvert {
	
	public static <T>T getValueByType(Object obj) {
		if(obj == null) 
			return null;
		
		if(obj instanceof Double) {
			if(((Double)obj) == 0) {
				return null;
			}
		}
		
		return (T)obj;
	}

}
