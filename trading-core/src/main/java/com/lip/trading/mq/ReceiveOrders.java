package com.lip.trading.mq;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveOrders {
  private static final String EXCHANGE_NAME = "orders";
  /**
   * 初始化
   */
  public static void init()
  {
    try{
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("localhost");
      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();

      channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
      String queueName = channel.queueDeclare().getQueue();
      System.out.println(" [*] Queue Name:"+queueName);
      channel.queueBind(queueName, EXCHANGE_NAME, "");

      Consumer consumer = new DefaultConsumer(channel) {
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope,
                                   AMQP.BasicProperties properties, byte[] body) throws IOException {
          String message = new String(body, "UTF-8");
          System.out.println(" [x] Received '" + message + "'");
        }
      };
      channel.basicConsume(queueName, true, consumer);
    }catch (Exception e)
    {
      //TODO
    }
  }
  public static void main(String[] argv) throws Exception {
    init();
  }
}