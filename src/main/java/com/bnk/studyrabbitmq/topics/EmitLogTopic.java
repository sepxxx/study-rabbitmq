package com.bnk.studyrabbitmq.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String routingKey ="quick.orange.rabbit";
            String message = "message";

            channel.basicPublish(EXCHANGE_NAME, routingKey,null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}
