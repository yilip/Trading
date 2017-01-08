package com.lip.trading.matchbox;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lip on 2016-12-19 11:44
 */
public class OrderBook {
    private Ask ask;
    private Bid bid;
    public List<Order>orderHistory;
    public OrderBook()
    {
        ask=new Ask();
        bid=new Bid();
        orderHistory=new ArrayList<>();
    }

    /**
     * 买单
     * @return
     */
    public List<Order>getAskOrder()
    {
        return ask.toList();
    }

    /**
     * 卖单
     * @return
     */
    public List<Order>getBidOrder()
    {
        return bid.toList();
    }
    /**
     * 处理新来的订单
     * @param order
     */
    public synchronized void process(Order order) {
        if(order.direction==Direction.BUY)
        {
            List<Order> list=bid.out(order);
            if(list==null||list.size()==0)//买单 价格小于卖1,只能放在ask中
            {
                ask.in(order);
            }else{//成交
                orderHistory.addAll(list);
                if(order.status==OrderStatus.PART_DEAL)//部分成交
                {
                    ask.in(order);
                }
            }
        }else if(order.direction==Direction.SELL)
        {
            List<Order> list=ask.out(order);
            if(list==null||list.size()==0)//卖单 价格大于卖1,只能放在bid中
            {
                bid.in(order);
            }else{//成交
                orderHistory.addAll(list);
                if(order.status==OrderStatus.PART_DEAL)//部分成交
                {
                    bid.in(order);
                }
            }
        }
    }

    /**
     * 根据订单号取消订单
     * @param id
     */
    public synchronized void cancel(long id,Direction direction)
    {
        if(direction==Direction.SELL)
            bid.cancel(id);
        else{
            ask.cancel(id);
        }
    }
    public static void print(List<Order>list)
    {
        System.out.println("***********list start********");
        for(Order order:list)
        {
            System.out.println(order);
        }
        System.out.println("***********list end********");
    }
    public static void main(String[] args) throws InterruptedException {
        //构建订单
        Ask ask = new Ask();
        Order o1 = new Order(1, 15.8, "5000001", 5, "100001", Direction.BUY, new Date());
        Order o2 = new Order(2, 14.5, "5000001", 3, "100001", Direction.BUY, new Date());
        Order o3 = new Order(3, 12.43, "5000001", 2, "100001", Direction.BUY, new Date());
        Order o4 = new Order(4, 15.6, "5000001", 1, "100001", Direction.BUY, new Date());
        Order o5 = new Order(5, 15.2, "5000001", 10, "100001", Direction.BUY, new Date());
        Thread.sleep(1 * 1000);
        Order o6 = new Order(6, 14.5, "5000001", 1, "100001", Direction.BUY, new Date());
        ask.in(o1);
        ask.in(o2);
        ask.in(o3);
        ask.in(o4);
        ask.in(o5);
        ask.in(o6);
        List<Order> list = ask.toList();
        print(list);
        Order sell=new Order(7, 15.15, "5000001", 18, "100002", Direction.SELL, new Date());
        List<Order>outList=ask.out(sell);
        print(outList);
        print(ask.toList());
        System.out.println(sell);
    }

}
