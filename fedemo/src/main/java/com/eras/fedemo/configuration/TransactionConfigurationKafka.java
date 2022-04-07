package com.eras.fedemo.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import com.eras.commonsdemo.model.Transaction;
import com.eras.commonsdemo.model.TransactionResult;

@Configuration
public class TransactionConfigurationKafka {

	@Bean
	public ReplyingKafkaTemplate<String, Transaction, TransactionResult> replyingTemplate(ProducerFactory<String, Transaction> pf,
			ConcurrentMessageListenerContainer<String, TransactionResult> repliesContainer) {

		return new ReplyingKafkaTemplate<>(pf, repliesContainer);
	}

	@Bean
	public ConcurrentMessageListenerContainer<String, TransactionResult> repliesContainer(
			ConcurrentKafkaListenerContainerFactory<String, TransactionResult> containerFactory) {

		ConcurrentMessageListenerContainer<String, TransactionResult> repliesContainer = containerFactory
				.createContainer("transactionReplies");
		repliesContainer.getContainerProperties().setGroupId("repliesGroup");
		repliesContainer.setAutoStartup(false);
		return repliesContainer;
	}

	@Bean
	public NewTopic transactionRequests() {
		return TopicBuilder.name("transactionRequests").partitions(10).replicas(2).build();
	}

	@Bean
	public NewTopic transactionReplies() {
		return TopicBuilder.name("transactionReplies").partitions(10).replicas(2).build();
	}
}
