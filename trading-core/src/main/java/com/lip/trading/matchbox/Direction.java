package com.lip.trading.matchbox;

/**
 * Created by Lip on 2017-01-10 14:36
 */
public enum Direction {
    BUY(0),SELL(1);
    private int value;
    Direction(int d)
    {
        this.value=d;
    }
}
