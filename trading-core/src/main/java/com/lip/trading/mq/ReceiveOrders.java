package com.lip.trading.mq;

import com.lip.trading.matchbox.Order;
import com.lip.trading.matchbox.OrderBook;
import com.lip.trading.util.SerializeUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class ReceiveOrders {
  private static final String EXCHANGE_NAME = "orders";
  private static int CPU_N=Runtime.getRuntime().availableProcessors()+1;
  private static BlockingQueue<Order>queue=new LinkedBlockingQueue<>();
  private static AtomicLong orderId=new AtomicLong(0);
  /**
   * 初始化 接收订单
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
          Order order= (Order) SerializeUtil.unserialize(body);
          /*********************/
          //TODO 验证order 是否满足各项要求
          /*********************/
          if(order!=null) {
            order.id=orderId.getAndIncrement();
            queue.add(order);
            //System.out.println(" [x] Received '" + order + "'");
          }
        }
      };
      channel.basicConsume(queueName, true, consumer);
    }catch (Exception e)
    {
      //TODO
    }
  }

  /**
   * 处理订单
   */
  public static void handleOrder()
  {
    final OrderBook orderBook=new OrderBook();
    ExecutorService ES= Executors.newFixedThreadPool(CPU_N);
    for(int i=0;i<CPU_N;i++) {
      ES.submit(new Runnable() {
        @Override
        public void run() {
          Order order = null;
          while (true) {
            try {
              order = queue.take();
              orderBook.process(order);
            } catch (InterruptedException e) {
              //TODO
            }
          }
        }
      });
    }
    ES.shutdown();
  }

  /**
   * 程序入口
   * @param argv
   * @throws Exception
   */
  public static void main(String[] argv) throws Exception {
    init();
    handleOrder();
    long s1=System.currentTimeMillis();
    while (true)
    {
      Thread.sleep(3*100);
      System.out.println(orderId.get()+" orders cost: "+(System.currentTimeMillis()-s1));
    }
  }
}