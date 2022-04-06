package com.eras.fedemo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eras.commonsdemo.model.Transaction;
import com.eras.commonsdemo.model.TransactionResult;
import com.eras.fedemo.services.kafka.TransactionProducer;

@RestController
public class TransactionController {

	private TransactionProducer producer;

	public TransactionController(TransactionProducer producer) {
		this.producer = producer;
	}

	//to simplify demo we have only posting new transaction
	@PostMapping("/transactions")
	public TransactionResult transaction(@RequestBody Transaction transaction) {
		return this.producer.produce(transaction);
	}

}
