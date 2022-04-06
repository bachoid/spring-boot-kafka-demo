package com.eras.commonsdemo.model;

public class TransactionResult {

	private Status status;

	private String msg;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "TransactionResult [status=" + status + ", msg=" + msg + "]";
	}
	
}
