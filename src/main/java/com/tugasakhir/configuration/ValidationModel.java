package com.tugasakhir.configuration;

import java.util.List;

public class ValidationModel<T> {
	List<T> model;
	String errMsg;

	public List<T> getModel() {
		return model;
	}

	public void setModel(List<T> model) {
		this.model = model;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}	
}
