package com.example.sqs.aws.sqs;

import com.example.sqs.dto.SqsMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class AwsSqsListener {

	// header ===> {LogicalResourceId=test-queue, ApproximateReceiveCount=1, SentTimestamp=1676220997581, ReceiptHandle=AQEBvMnj0MQOBgFhU/D/x/FyULhn4NzoNn2hfkXFyXt/wf7N1XOJ3ahLPZyvfcnfT4g2D8YhIcitkpPifTMviHqUoO9tpz0zUEk0rNqUkM3DbpAVLZ9xUZrqa7NOCWi/SCgggYXaVaDcwU/C3O+DlPnie2KWepUwcwazgFq/7i2DCKSCyvHfvk5Ah1QVw3zdrQ+LM7IFHDRQ8EQZjJj1dTz7jwbnOfIt6fzfybb6W8vlq1iu6wvWFGaAqnPo84cs0Yj9Pr510fUh3GBhaXrj6sn2Prv26B3YppFikReK9zUO+NaTuWniP7wSyrWL6hwgtFimnc4FbGQgA1usWu6RRrFeWBztNklL64Yn9GiMKHubX+FIvjkwzSlO24vJYgsfUN8b2OaSKIQ+fYgqKXlu3bndXw==, Acknowledgment=org.springframework.cloud.aws.messaging.listener.QueueMessageAcknowledgment@7b021dd0, Visibility=org.springframework.cloud.aws.messaging.listener.QueueMessageVisibility@7672342e, SenderId=AIDA4ZZXLH7WUZDLXCXWN, lookupDestination=test-queue, ApproximateFirstReceiveTimestamp=1676220997583, MessageId=d532473d-a746-467f-9d30-ad7c7984ec1c}
	@SqsListener(value = "${cloud.aws.sqs.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
	public void listen(@Payload SqsMessage message, @Headers Map<String, String> headers, Acknowledgment ack) {
		log.info("header ===> {}", headers);
		log.info("message ===> {}", message);
		ack.acknowledge();
	}

}