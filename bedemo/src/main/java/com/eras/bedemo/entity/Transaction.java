package com.eras.bedemo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {

	// to simplify demo using only string to represent transaction
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

}
