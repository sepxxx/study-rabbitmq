package com.bnk.studyrabbitmq.publishsubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory  = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String message = "info: Hello world!";
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }


}
