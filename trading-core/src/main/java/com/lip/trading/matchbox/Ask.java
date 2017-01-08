package com.lip.trading.matchbox;


import com.lip.trading.ApplicationException;
import com.lip.trading.Queue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lip on 2016-12-19 11:44
 * 买单
 */
public class Ask extends Queue{
    private OrderNode orderNode;

    public Ask() {

    }
    /**
     * 添加一个订单
     * 按照价格优先，时间其次的顺序插入到队列中
     *
     * @param order
     */
    public synchronized void in(Order order) {
        if (order == null)
            throw new NullPointerException("order is null");
        if(order.direction!= Direction.BUY)
            throw new ApplicationException("trading direction  error");
        if (orderNode == null) {
            orderNode = new OrderNode(order);
            return;
        }
        if(order.price>orderNode.order.price)
        {
            OrderNode newRoot=new OrderNode(order);
            newRoot.next=orderNode;
            orderNode=newRoot;
            return;
        }
        OrderNode temp = orderNode;
        while (temp.next != null&&order.price <= temp.next.order.price && order.time.getTime() >= temp.next.order.time.getTime()) {
            temp = temp.next;
        }
        //插入新的订单
        OrderNode temp1=temp.next;
        temp.next = new OrderNode(order);
        temp.next.next=temp1;
    }

    /**
     * some one want to sell  and bid a price under ask 1
     *  新的成交订单
     * @param order 成交订单
     */
    public synchronized List<Order> out(Order order)
    {
        List<Order>list=new ArrayList<>();
        //no ask
        if(orderNode==null||order.num<=0)
            return list;
        if(orderNode.order.price<order.price)
            return list;
        OrderNode temp=orderNode;
        int n=order.left;
        while (n>0&&temp!=null)
        {
            if(temp.order.price>=order.price) {
                if(temp.order.left > n)
                {
                    temp.order.left -= n;
                    temp.order.status= OrderStatus.PART_DEAL;
                    list.add(temp.order);
                    order.status= OrderStatus.DEAL;
                    return list;
                }
                else if (temp.order.left == n) {
                    temp.order.left = 0;
                    temp.order.status= OrderStatus.DEAL;
                    order.status= OrderStatus.DEAL;
                    list.add(temp.order);
                    return list;
                } else {
                    n -= temp.order.left;
                    temp.order.left = 0;
                    temp.order.status= OrderStatus.DEAL;
                    list.add(temp.order);
                    temp = temp.next;
                    order.status= OrderStatus.PART_DEAL;
                    /************************/
                    OrderNode temp2 = orderNode.next;
                    orderNode.next = null;
                    orderNode = temp2;
                    /************************/
                }
            }else {
                break;
            }
        }
        return list;
    }

}
