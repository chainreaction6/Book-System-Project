package com.trilogyed.notequeueconsumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * Note-Queue-Consumer
 * Purpose: To provide configuration for the queue system.
 * Needed Annotations:
 * @EnableFeignClients - must use feign client to reach out to other service
 * @EnableDiscoveryClient - must register with eureka, spring.application.name=note-queue
 */

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class NoteQueueConsumerApplication {
	public static final String TOPIC_EXCHANGE_NAME = "note-exchange";
	public static final String QUEUE_NAME = "note-queue";
	public static final String ROUTING_KEY = "note.#";

	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(TOPIC_EXCHANGE_NAME);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	}

	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public static void main(String[] args) {
		SpringApplication.run(NoteQueueConsumerApplication.class, args);
	}

}
