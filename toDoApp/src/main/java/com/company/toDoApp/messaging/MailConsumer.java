package com.company.toDoApp.messaging;

import com.company.toDoApp.config.RabbitMQConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MailConsumer {

    private  final MailService mailService;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public MailConsumer(MailService mailService) {
        this.mailService = mailService;
    }
    @RabbitListener(queues = RabbitMQConfig.mailQueue)
    public void receiveMessage(String message) {
        try {
            JsonNode jsonNode= objectMapper.readTree(message);
            String recipientEmail = jsonNode.get("mail").asText();
            String bodyMessage = jsonNode.get("message").asText();
            mailService.sendSimpleMessage(recipientEmail, "Email Confirmation", bodyMessage);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
