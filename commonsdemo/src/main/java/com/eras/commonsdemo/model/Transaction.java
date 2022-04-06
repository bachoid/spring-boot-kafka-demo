package com.eras.commonsdemo.model;

public class Transaction {

	// to simplify demo using only string to represent transaction
	private long id;
	private String data;

	public Transaction() {
		super();
	}

	public Transaction(long id, String data) {
		super();
		this.id = id;
		this.data = data;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", data=" + data + "]";
	}

}
