package com.eras.bedemo.services.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.eras.commonsdemo.model.TransactionResult;

@Service
public class TransactionResultProducer {

	private static final Logger LOG = LoggerFactory.getLogger(TransactionResultProducer.class);
	private static final String TOPIC = "transactionReplies";

	private KafkaTemplate<String, TransactionResult> template;

	public TransactionResultProducer(KafkaTemplate<String, TransactionResult> template) {
		this.template = template;
	}

	public void produce(TransactionResult transactionResult) {
		this.template.send(TOPIC, transactionResult);
		LOG.info("sent transaction result {}", transactionResult);
	}
}
