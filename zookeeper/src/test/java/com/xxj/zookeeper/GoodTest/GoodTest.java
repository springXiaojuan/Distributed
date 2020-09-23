package com.xxj.zookeeper.GoodTest;


import com.xxj.zookeeper.service.IGoodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author  xuxiaojuan
 * @date  2020/9/22 5:01 下午
 */
@SpringBootTest
public class GoodTest {

    // 倒计数器
    CountDownLatch countDownLatch = new CountDownLatch(1);

    @Autowired
    private IGoodService goodService;

    @Test
    public void updatess() {
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        for(int i = 0; i < 100; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        System.out.println("Thread: " + Thread.currentThread().getName() + ", time: " +System.currentTimeMillis());
                        goodService.updateByPrimaryKeyStore(1L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            countDownLatch.countDown();
        }

        try {
            executorService.shutdown();
            if(executorService.awaitTermination(6000, TimeUnit.MILLISECONDS)){
                executorService.shutdown();
            }
        } catch (InterruptedException e) {
            System.out.println("awaitTermination interrupt: " + e);
            e.printStackTrace();
        }
    }

}
