package com.xxj.redis.spring.service;

import com.xxj.redis.config.RedisConfig;
import com.xxj.redis.service.GoodsService;
import com.xxj.redis.utils.ConvertArray;
import io.lettuce.core.RedisFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * @author xuxiaojuan
 * @date 2020/11/20 2:48 下午
 */

@Service
public class LettuceService {

    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private GoodsService goodsService;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void testStr() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        RedisTemplate<Object, Object> objectObjectRedisTemplate = new RedisTemplate<>();

        scheduledExecutorService.schedule(() -> {
            for (int i = 0; i < 20; i++) {
                redisConfig.redisTemplate().boundValueOps(ConvertArray.STORE_PREFIX + i).set(String.valueOf(50));
            }
        },3, TimeUnit.SECONDS);

        scheduledExecutorService.shutdown();

        try {
            if(!scheduledExecutorService.awaitTermination(30,TimeUnit.SECONDS)){
                scheduledExecutorService.shutdown();
            }
        }catch (Exception e) {
            e.printStackTrace();
            scheduledExecutorService.shutdown();
        }
    }

    public void test() {
        Thread[] threads=new Thread[16];

        for (int i = 0; i < 1500; i++) {
            Thread thread = new Thread("线程" + i) {
                @Override
                public void run() {
                    goodsService.updateGoodStore(1L);
                }
            };
            threads[i]=thread;
            thread.start();
        }

        //等待所有数组中的线程执行结束
        for(Thread thread:threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void runThread() {
        //创建一个确定的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        for (int i=0; i < 990; i++) {
            //提交线程到线程池去执行
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        //等待，线程就位，但是不运行
                        //countDownLatch.await();
                        System.out.println("Thread:"+Thread.currentThread().getName() + ", time: "+System.currentTimeMillis());
                        try {
                            //TODO 执行业务代码 (超卖测试)
                            goodsService.updateGoodStore(1L);

                        } catch (Throwable e) {
                            e.printStackTrace();
                            System.out.println("Thread:"+Thread.currentThread().getName() + e.getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        //倒计算器 -1，那么16个线程就同时开始执行，那么就达到并发效果
        countDownLatch.countDown();

        try {
            // 传达完毕信号，等任务执行完才关闭
            executorService.shutdown();
            // (所有的任务都结束的时候，返回TRUE)
            if(!executorService.awaitTermination(60000, TimeUnit.MILLISECONDS)){
                // 超时的时候向线程池中所有的线程发出中断(interrupted)，立刻马上关闭
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            System.out.println("awaitTermination interrupted: " + e);
            executorService.shutdownNow();
        }
    }

}
