package com.lip.trading;

import com.lip.trading.matchbox.Order;
import com.lip.trading.matchbox.OrderNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lip on 2016-12-21 18:16
 */
public abstract class Queue {
    protected OrderNode orderNode;
    public  abstract  List<Order> out(Order order);
    public  abstract void in(Order order);

    public Queue()
    {
    }
    public Order get()
    {
        return  orderNode.order;
    }
    /**
     * 取消一个订单
     * @return
     * @param id
     */
    public synchronized boolean cancel(long id)
    {
        if(orderNode!=null&&orderNode.order.id==id)
        {
            OrderNode temp=orderNode.next;
            orderNode.next=null;
            orderNode=temp;
            return true;
        }
        OrderNode temp=orderNode;
        while (temp.next!=null)
        {
            if(temp.next.order.id==id)
            {
                OrderNode temp1=temp.next.next;
                temp.next.next=null;
                temp.next=temp1;
                return true;
            }else{
                temp=temp.next;
            }
        }
        return false;
    }

    /**
     * get the order list
     * @return
     */
    public List<Order>toList()
    {
        List<Order>list=new ArrayList<>();
        OrderNode temp=orderNode;
        while (temp!=null)
        {
            list.add(temp.order);
            temp=temp.next;
        }
        return list;
    }
}
