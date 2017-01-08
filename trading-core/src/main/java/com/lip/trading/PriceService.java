package com.lip.trading;


import com.lip.trading.util.MathUtil;

import java.util.Random;

/**
 * Created by Lip on 2016-12-14 11:56
 */
public class PriceService {
    private static volatile double BASE_PRICE=3000;
    private static Random random=new Random();
    //private List<Double>priceList=new ArrayList<>(1000);
    public static double getPrice()
    {
        return BASE_PRICE;
    }
    public  static  void update()
    {
        int a=random.nextInt(10);
        if(a<3)//跌
        {
            BASE_PRICE= MathUtil.roundup(BASE_PRICE-random.nextDouble()/5.0,2);
        }else{
            BASE_PRICE=MathUtil.roundup(BASE_PRICE+random.nextDouble()/5.0,2);
        }
    }
    public static void main(String[] args) {
        for(int i=0;i<10;i++) {
            update();
            System.out.println(getPrice());
        }
    }
}
