package com.xxj.zookeeper.lock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * @author  xuxiaojuan
 * @date  2020/8/31 9:48 下午
 */
public class MyZookeeperWatcher  implements Watcher {

    private CountDownLatch countDownLatch;

    public MyZookeeperWatcher(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getType().equals(Event.EventType.NodeDeleted)) {
            countDownLatch.countDown();
        }
    }
}
