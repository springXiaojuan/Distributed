package com.xxj.redis;

import com.xxj.redis.service.GoodsService;
import com.xxj.redis.spring.service.LettuceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author xuxiaojuan
 * @date 2020/11/23 2:29 下午
 */
@SpringBootTest
public class JedisLockTest {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private LettuceService lettuceService;

    private static CountDownLatch countDownLatch = new CountDownLatch(1);


    @Test
    public void testJedisLock2() {
        Thread[] threads=new Thread[1];

        for (int i = 0; i < 1; i++) {
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

    @Test
    public  void testJedisLock() {
        ExecutorService executorService = Executors.newFixedThreadPool(16);

        for (int i = 0; i < 1500; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        System.out.println(Thread.currentThread().getName() + " time : " + System.currentTimeMillis());
                        try {
                            goodsService.updateGoodStore(1L);
                            System.out.println("222");
                        }catch (Exception e) {
                            System.out.println("thread:" + Thread.currentThread().getName() + "    " + e.getMessage());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        countDownLatch.countDown();
        try {
            executorService.shutdown();
            if(executorService.awaitTermination(60000, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        }catch (Exception e) {
            System.out.println("awaitTermination interrupted :" + e);
            executorService.shutdown();
        }
    }

    @Test
    public void test() {
        lettuceService.test();
    }

    @Test
    public void initData() {
        lettuceService.testStr();
    }

    @Test
    public void runThread() {
        lettuceService.runThread();
    }



}
