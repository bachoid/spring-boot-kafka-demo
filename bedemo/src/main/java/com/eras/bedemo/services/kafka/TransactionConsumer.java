package com.eras.bedemo.services.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.eras.bedemo.repository.TransactionRepository;
import com.eras.commonsdemo.model.Status;
import com.eras.commonsdemo.model.Transaction;
import com.eras.commonsdemo.model.TransactionResult;

@Service
public class TransactionConsumer {

	private final Logger LOG = LoggerFactory.getLogger(TransactionConsumer.class);
	private static final String TOPIC = "transactionRequests";

	private TransactionRepository repository;

	private TransactionResultProducer resultProducer;

	public TransactionConsumer(TransactionRepository repository) {
		super();
		this.repository = repository;
	}

	@KafkaListener(topics = TOPIC, groupId = "group_id")
	public void consume(Transaction transaction) {
		com.eras.bedemo.entity.Transaction trans = new com.eras.bedemo.entity.Transaction();
		trans.setData(transaction.getData());

		TransactionResult transactionResult = new TransactionResult();
		try {
			repository.save(trans);
			LOG.info("saved transaction to db {}", transaction);
			transactionResult.setStatus(Status.SUCCESS);
			transactionResult.setMsg("saved transaction to db");

			resultProducer.produce(transactionResult);
		} catch (Exception e) {
			LOG.error("saving transaction failed {}", transaction, e);
			transactionResult.setStatus(Status.FAIL);
			transactionResult.setMsg("saving transaction failed: " + e.getMessage());
			resultProducer.produce(transactionResult);
		}
	}

}
