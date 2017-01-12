package com.lip.trading.matchbox;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by Lip on 2016-12-19 11:45
 * 订单
 */
public class Order implements Serializable{
    public long id;
    public double price;
    public Date time;
    public String symbol;
    public int num;
    public int left;
    public String userId;
    public Direction direction;
    public OrderStatus status;
    public Order()
    {

    }
    public Order(long _id, double _price, String _symbol, int _num, String _userId, Direction _d, Date _time)
    {
        this.id=_id;
        this.price=_price;
        this.symbol=_symbol;
        this.num=_num;
        this.direction=_d;
        this.time=_time;
        this.userId=_userId;
        this.left=num;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", price=" + price +
                ", time=" + time +
                ", symbol='" + symbol + '\'' +
                ", num=" + num +
                ", left=" + left +
                ", userId='" + userId + '\'' +
                ", direction=" + direction +
                ", status=" + status +
                '}';
    }
}
