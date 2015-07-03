package com.may.ple.sahai.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BuySaleTaskResp extends CommonResp {
	private List<TaskInfoResp> tasks;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	public List<TaskInfoResp> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskInfoResp> tasks) {
		this.tasks = tasks;
	}

}
