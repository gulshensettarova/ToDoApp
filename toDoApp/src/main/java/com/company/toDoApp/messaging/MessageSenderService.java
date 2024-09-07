package com.company.toDoApp.messaging;

import com.company.toDoApp.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderService {

    private final RabbitTemplate rabbitTemplate;

    public MessageSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageToQueue(String message,String recipientEmail) {
        String messageAndRecipientMail = String.format("{\"mail\":\"%s\", \"message\":\"%s\"}", recipientEmail, message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.mailExchange, RabbitMQConfig.mailRoutingKey,messageAndRecipientMail);
    }
}
