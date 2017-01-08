package com.lip.trading.matchbox;

/**
 * Created by Lip on 2016-12-19 11:59
 */
public class OrderNode {
    public Order order;
    public OrderNode next;
    //TODO 兄弟节点
    public OrderNode sibling;
    public OrderNode()
    {

    }
    public OrderNode(Order _order)
    {
        this.order=_order;
    }
}
