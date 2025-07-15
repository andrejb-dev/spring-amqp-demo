package sk.abadinka.springamqpdemo;

import org.springframework.amqp.rabbit.core.BrokerEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@EnableScheduling
@Profile("sender")
public class MessageSender implements ApplicationListener<BrokerEvent> {

	private boolean isBrokerAvailable = true;
	private final RabbitTemplate rabbitTemplate;
	private final AtomicInteger counter = new AtomicInteger(0);

	public MessageSender(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}


	@Scheduled(fixedRate = 1000) // Runs every 5 seconds
	public void scheduled() {
		if (isBrokerAvailable) {
			rabbitTemplate.convertAndSend("demoQueue", "Hello, DEMO QUEUE with msg:" + counter.incrementAndGet());
			rabbitTemplate.convertAndSend(SpringAmqpDemoApplication.FANOUT_EXCHANGE, "", "Hello, FANOUT num:" + counter.get());
			rabbitTemplate.convertAndSend(SpringAmqpDemoApplication.TOPIC_EXCHANGE, "topic.important.message", "Hello, TOPIC with important message num:" + counter.get());
			rabbitTemplate.convertAndSend(SpringAmqpDemoApplication.TOPIC_EXCHANGE, "topic.error.message", "Hello, TOPIC with error message num:" + counter.get());
			System.out.println("Messages pack no. " + counter.get() + " sent to rabbitMQ.");
		} else {
			System.out.println("Broker is not available, message not sent.");
		}
	}

	@Override
	public void onApplicationEvent(@NonNull BrokerEvent event) {
//		this.isBrokerAvailable = event.isBrokerAvailable();
		System.out.println("Broker availability changed to: " + isBrokerAvailable);
		System.out.printf("BrokerEvent received: " + event);
	}
}
