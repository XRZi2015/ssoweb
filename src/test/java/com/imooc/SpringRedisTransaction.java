package com.imooc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-redis-single.xml"})
public class SpringRedisTransaction {
    @Autowired
    private JedisPool jedisPool;

    @Test
    public void testTransaction() {
        // 通过pool获取Jedis实例
        Jedis jedis = jedisPool.getResource();
        // 实刷额度
        int amtToSubtract = 10;

        Money money = new Money();
        //余额
        Integer bank = money.getBank();
        //欠额
        Integer arrears = money.getArrears();
        //redis中初始化
        jedis.set("bank", bank + "");
        jedis.set("arrears", arrears + "");

        //监控 余额
 /*       jedis.watch("bank");*/

        //启动一个线程改变 余额的金额
        Thread thread = new Thread(new ThreadDemo());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Transaction transaction = jedis.multi();

        transaction.decrBy("bank", amtToSubtract);
        transaction.incrBy("arrears", amtToSubtract);
        transaction.exec();

        System.out.println("bank \t" + jedis.get("bank"));
        System.out.println("arrears \t" + jedis.get("arrears"));

        // 释放资源
        jedis.close();
        jedisPool.close();
    }
}

class ThreadDemo implements Runnable {
    @Override
    public void run() {
        // 创建Jedis实例
        Jedis jedis = new Jedis("192.168.84.128", 6379);
        //改变余额为2 元,破坏 监控
        jedis.set("bank", 50+ "");
        // 释放资源
        jedis.close();
    }
}
