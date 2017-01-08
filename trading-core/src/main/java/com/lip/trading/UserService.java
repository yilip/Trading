package com.lip.trading;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lip on 2016-12-14 14:15
 */
public class UserService {
    public static Map<String,Double>userMoney=new ConcurrentHashMap<>();
    public static void init()
    {
        userMoney.put("100000",10000000.0);
        Long id=100001l;
        for(int i=0;i<1000;i++)
        {
            userMoney.put((id+i)+"",10000.0);
        }
    }

    /**
     * 获取用户资金
     * @param userId
     * @return
     */
    public static double getUserMoney(String userId)
    {
        return userMoney.get(userId);
    }

    /**
     * 更新用户余额
     * @param userId
     * @param delta
     */
    public static void update(String userId,double delta)
    {
        synchronized (userId.intern()) {
            double money=userMoney.get(userId);
            if(money<0)
                throw new ApplicationException("用户余额不足");
            userMoney.put(userId, userMoney.get(userId)+delta);
        }
    }
    public static String getRandomUser()
    {
        Random random=new Random();
        return (100001+random.nextInt(1000))+"";
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++) {
            System.out.println(getRandomUser());
        }
    }
}
