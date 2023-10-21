package com.bnk.studyrabbitmq.publishsubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RecieveLog {

    private static final String EXCHANGE_NAME = "direct_logs";
    public static void main(String[] args) {
        ConnectionFactory  connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try{
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            String queueName = channel.queueDeclare().getQueue();
            String[] severities = {"info", "error", "warning"};
            for(String severity: severities) {
                channel.queueBind(queueName, EXCHANGE_NAME, severity);
            }
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
