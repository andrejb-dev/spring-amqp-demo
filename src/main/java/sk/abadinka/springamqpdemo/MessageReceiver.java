package sk.abadinka.springamqpdemo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("receiver")
public class MessageReceiver {

	@RabbitListener(queues = "demoQueue")
	public void listen(String message) throws InterruptedException {
		System.out.println("Received queue message: " + message);
		Thread.sleep(6000); // Simulate processing time
	}

	@RabbitListener(queues = {SpringAmqpDemoApplication.FANOUT_Q1})
	public void receiveMessageFromFanout1(String message) throws InterruptedException {
		System.out.println("Received fanout 1 message: " + message);
		Thread.sleep(2000); // Simulate processing time
	}

	@RabbitListener(queues = {SpringAmqpDemoApplication.FANOUT_Q2})
	public void receiveMessageFromFanout2(String message) throws InterruptedException {
		System.out.println("Received fanout 2 message: " + message);
		Thread.sleep(1000); // Simulate processing time
	}

	@RabbitListener(queues = {SpringAmqpDemoApplication.TOPIC_Q1})
	public void receiveMessageFromTopic1(String message) throws InterruptedException {
		System.out.println("Received topic 1 message: " + message);
		Thread.sleep(1500); // Simulate processing time
	}

	@RabbitListener(queues = {SpringAmqpDemoApplication.TOPIC_Q2})
	public void receiveMessageFromTopic2(String message) throws InterruptedException {
		System.out.println("Received topic 2 message: " + message);
		Thread.sleep(400); // Simulate processing time
	}
}
