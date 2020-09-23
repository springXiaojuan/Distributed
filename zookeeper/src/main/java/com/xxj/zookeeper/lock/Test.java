package com.xxj.zookeeper.lock;

import com.xxj.zookeeper.mapper.GoodServiceMapper;
import com.xxj.zookeeper.service.IGoodService;
import com.xxj.zookeeper.service.impl.GoodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author  xuxiaojuan
 * @date  2020/9/11 3:37 下午
 */
public class Test {

    // 倒计数器
    CountDownLatch countDownLatch = new CountDownLatch(1);

    @Autowired
    private static IGoodService goodService;


    public static void main(String[] args) {
//        Test test = new Test();
        goodService.updateByPrimaryKeyStore(1L);

//        test.runThread();
    }

    public void runThread() {
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
