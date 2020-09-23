package com.xxj.zookeeper.IdGenerate;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author  xuxiaojuan
 * @date  2020/8/30 10:12 上午
 */
public  class  IdGenerate01 {

    private String ID_NODE = "/id";

    CuratorFramework client = null;
    CountDownLatch countDownLatch = new CountDownLatch(1);

    public  IdGenerate01() {
        RetryPolicy policy = new ExponentialBackoffRetry(5000,3);
        client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1")
                .connectionTimeoutMs(5000)
                .sessionTimeoutMs(5000)
                .retryPolicy(policy)
                .build();
        client.start();
    }

    public static void main(String[] args) throws Exception {
        IdGenerate01 idGenerate01 = new IdGenerate01();
//        String s = idGenerate01.IdGenerate();
//        System.out.println(s);
        idGenerate01.runThread();
    }

    public String IdGenerate() throws Exception {
        Stat stat = client.checkExists().forPath(ID_NODE);
        if(null == stat) {
            String s = client.create()
                    .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath(ID_NODE);
//            System.out.println(s);
            return s;
        }
        return null;
    }

    /**
     * 多并发执行
     */
    private void runThread() {
        final Long awaitTime = 5000L;
        IdGenerate01 idGenerate01 = new IdGenerate01();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i = 0; i < 100; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        System.out.println("Thread:"+Thread.currentThread().getName() + ", time: "+System.currentTimeMillis());
                        try {
                            String s = IdGenerate();
                            System.out.println(s);
                        } catch (Exception e) {
                            System.out.println("Thread :" + Thread.currentThread().getName() + "message" +e.getMessage());
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        countDownLatch.countDown();

        //关闭线程池的代码
        try {
            // 传达完毕信号
            executorService.shutdown();
            // (所有的任务都结束的时候，返回TRUE)
            if(!executorService.awaitTermination(awaitTime, TimeUnit.MILLISECONDS)){
                // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            System.out.println("awaitTermination interrupted: " + e);
            executorService.shutdownNow();
        }


    }



}
