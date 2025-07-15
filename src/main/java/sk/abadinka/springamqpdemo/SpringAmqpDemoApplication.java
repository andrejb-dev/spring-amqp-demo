package sk.abadinka.springamqpdemo;

import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAmqpDemoApplication {

	public static final String TOPIC_Q1 = "topic.queue1";
	public static final String TOPIC_Q2 = "topic.queue2";
	public static final String TOPIC_EXCHANGE = "topic.exchange";
	public static final String FANOUT_EXCHANGE = "fanout.exchange";
	public static final String FANOUT_Q1 = "fanout.queue1";
	public static final String FANOUT_Q2 = "fanout.queue2";

	public static void main(String[] args) {
		SpringApplication.run(SpringAmqpDemoApplication.class, args);
	}

	@Bean
	public Queue demoQueue() {
		return new Queue("demoQueue");
	}

	@Bean
	public Declarables fanoutBindings() {
		Queue fanoutQueue1 = new Queue(FANOUT_Q1, false);
		Queue fanoutQueue2 = new Queue(FANOUT_Q2, false);
		FanoutExchange fanoutExchange = new FanoutExchange(FANOUT_EXCHANGE);

		return new Declarables(
				fanoutQueue1,
				fanoutQueue2,
				fanoutExchange,
				BindingBuilder.bind(fanoutQueue1).to(fanoutExchange),
				BindingBuilder.bind(fanoutQueue2).to(fanoutExchange));
	}

	@Bean
	public Declarables topicBindings() {
		Queue topicQueue1 = new Queue(TOPIC_Q1, false);
		Queue topicQueue2 = new Queue(TOPIC_Q2, false);

		TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE);

		return new Declarables(
				topicQueue1,
				topicQueue2,
				topicExchange,
				BindingBuilder
						.bind(topicQueue1)
						.to(topicExchange).with("*.important.*"),
				BindingBuilder
						.bind(topicQueue2)
						.to(topicExchange).with("#.error.#"));
	}
}
