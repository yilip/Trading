package com.lip.trading;

import com.lip.trading.util.MathUtil;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Lip on 2016-12-14 11:56
 */
public class Main {
    static AtomicLong id=new AtomicLong(1);
    static ScheduledExecutorService service= Executors.newSingleThreadScheduledExecutor();
    static Random random=new Random();
    public static void main(String[] args) throws InterruptedException {
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                PriceService.update();
            }
        },10,300, TimeUnit.MILLISECONDS);
        //初始化用户
        UserService.init();
        ExecutorService ES=Executors.newFixedThreadPool(4);
        final IndexTradeTree root=IndexTradeTree.getRootTree(PriceService.getPrice());
        for(int i=0;i<10;i++)
        {
            String userId=UserService.getRandomUser();
            IndexTradeModel tradeModel=null;
            if(random.nextInt(10)<4)
            {
                tradeModel=initTrade(userId,1);
            }else{
                tradeModel=initTrade(userId,0);
            }
            root.addIndexTrade(tradeModel);
            System.out.println("root size:"+root.size());
        }
        System.out.println("***************init************");
        System.out.println("above:" + root.getAbovePrice(3000));
        System.out.println("root size:"+root.size());
        System.out.println("below:" + root.getBelowPrice(3000));
        System.out.println("root size:"+root.size());
        System.out.println("*******************************");
        while (true) {
            try {
                Thread.sleep(1 * 1000);
                double p = PriceService.getPrice();
                System.out.println("above:" + root.getAbovePrice(p));
                System.out.println("root size:"+root.size());
                System.out.println("below:" + root.getBelowPrice(p));
                System.out.println("root size:"+root.size());
            }catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("exception:"+e.getMessage());
            }
        }
    }

    /**
     * 创建一个订单
     * @param direction 看涨-1 还是看跌-0
     * @return
     */
    public static IndexTradeModel createTrade(String userId,Integer direction)
    {
        IndexTradeModel indexTradeModel=new IndexTradeModel();
        indexTradeModel.setId(id.getAndIncrement());
        double price=PriceService.getPrice();
        indexTradeModel.setOpenPrice(price);
        indexTradeModel.setDirection(direction);
        indexTradeModel.setStatus(IndexTradeModel.STATUS_OPEN);
        indexTradeModel.setUserId(userId);
        if(direction.equals(1))
        {
            indexTradeModel.setStopProfit(price+2);
            indexTradeModel.setStopLoss(price-2*0.8);
        }else {
            indexTradeModel.setStopProfit(price-2);
            indexTradeModel.setStopLoss(price+2*0.8);
        }
        return indexTradeModel;
    }
    public static IndexTradeModel initTrade(String userId,Integer direction)
    {
        IndexTradeModel indexTradeModel=new IndexTradeModel();
        indexTradeModel.setId(id.getAndIncrement());
        double price=3000;
        indexTradeModel.setOpenPrice(price);
        indexTradeModel.setDirection(direction);
        indexTradeModel.setStatus(IndexTradeModel.STATUS_OPEN);
        indexTradeModel.setUserId(userId);
        if(direction.equals(1))
        {
            indexTradeModel.setStopProfit(MathUtil.roundup(price+random.nextDouble()*2,2));
            indexTradeModel.setStopLoss(MathUtil.roundup(price-0.8*random.nextDouble()*2,2));
        }else {
            indexTradeModel.setStopProfit(MathUtil.roundup(price-random.nextDouble()*2,2));
            indexTradeModel.setStopLoss(MathUtil.roundup(price+0.8*random.nextDouble()*2,2));
        }
        return indexTradeModel;
    }
}
