package com.eras.fedemo.services.kafka;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.eras.commonsdemo.model.Status;
import com.eras.commonsdemo.model.Transaction;
import com.eras.commonsdemo.model.TransactionResult;

@Service
public class TransactionProducer {

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TransactionProducer.class);

	private ReplyingKafkaTemplate<String, Transaction, TransactionResult> template;

	public TransactionProducer(ReplyingKafkaTemplate<String, Transaction, TransactionResult> replyingTemplate) {
		this.template = replyingTemplate;
	}

	public TransactionResult produce(Transaction transaction) {
		ProducerRecord<String, Transaction> record = new ProducerRecord<>("transactionRequests", transaction);
		RequestReplyFuture<String, Transaction, TransactionResult> replyFuture = template.sendAndReceive(record);
		SendResult<String, Transaction> sendResult = null;
		try {
			sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			LOG.error("thread interrupted");
			Thread.currentThread().interrupt();
			return getTransactionResult(Status.FAIL, "Thread interrupted");
		} catch (ExecutionException e) {
			LOG.error("Error when sending transaction", e);
			return getTransactionResult(Status.FAIL, e.getMessage());
		} catch (TimeoutException e) {
			LOG.error("Timeout when sending transaction", e);
			return getTransactionResult(Status.FAIL, e.getMessage());
		}

		LOG.info("Sent ok: " + sendResult.getRecordMetadata());

		ConsumerRecord<String, TransactionResult> consumerRecord = null;
		try {
			consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			LOG.error("thread interrupted");
			Thread.currentThread().interrupt();
			return getTransactionResult(Status.FAIL, "Thread interrupted");
		} catch (ExecutionException e) {
			LOG.error("Error when getting transaction reply", e);
			return getTransactionResult(Status.FAIL, e.getMessage());
		} catch (TimeoutException e) {
			LOG.error("Timeout when getting transaction reply", e);
			return getTransactionResult(Status.FAIL, e.getMessage());
		}
		LOG.info("Return value: {}", consumerRecord.value());

		return consumerRecord.value();
	}

	private TransactionResult getTransactionResult(Status status, String msg) {
		TransactionResult transactionResult = new TransactionResult();
		transactionResult.setMsg(msg);
		transactionResult.setStatus(status);
		return transactionResult;
	}

}
