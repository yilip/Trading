package com.lip.trading.mq;

import com.lip.trading.UserService;
import com.lip.trading.matchbox.Direction;
import com.lip.trading.matchbox.Order;
import com.lip.trading.matchbox.OrderStatus;
import com.lip.trading.util.MathUtil;
import com.lip.trading.util.SerializeUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Lip on 2017-01-10 11:53
 */
public class SendOrders {
    private static Random random=new Random();
    private static final String EXCHANGE_NAME = "orders";

    public static void main(String[] argv)
            throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        Scanner scanner=new Scanner(System.in);
        Order order=null;
        while(!"q".equalsIgnoreCase("")) {
            order=getRandomOrder();
            channel.basicPublish(EXCHANGE_NAME, "", null, SerializeUtil.serialize(order));
        }
        channel.close();
        connection.close();
    }
    public static Order getRandomOrder()
    {
        double bPrice=15.0,r=1;
        Order order=new Order();
        if(random.nextBoolean())
            order.direction= Direction.BUY;
        else
            order.direction= Direction.SELL;
        if(random.nextBoolean())
            r=-1;
        //order.id=orderId.getAndIncrement();
        order.price= MathUtil.roundup(bPrice+3*random.nextDouble()*r,2);
        order.symbol="5000001";
        order.userId= UserService.getRandomUser();
        order.status= OrderStatus.NEW;
        order.time=new Date();
        order.num=1+random.nextInt(20);
        order.left=order.num;
        return order;
    }
}
