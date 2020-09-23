package com.xxj.zookeeper.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;

/**
 * @author  xuxiaojuan
 * @date  2020/8/30 8:07 下午
 */
public class ZookeeperLock {

    /**
     * 创建zookeeper连接
     * 加锁，
     * 解锁
     */

    // 锁节点的名字
    private String lockRootName = "/lock";
    private String lockName;

    private String zkAddress = "127.0.0.1:2181";
    private int sessionTimeout = 5000;
    private byte[] bytes = new byte[0];
    private ZooKeeper zooKeeper;
    private String currentLockName;

    // 创建一个倒计数器
    private CountDownLatch  countDownLatch  = new CountDownLatch(1);

    // 创建一个zookeeper client
    public ZookeeperLock(String lockName) {
        this.lockName = lockName;
        try {
            zooKeeper = new ZooKeeper(zkAddress, sessionTimeout, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState().equals(Event.KeeperState.SyncConnected)) {
                        countDownLatch.countDown();
                    }
                }
            });
            // 等待，阻塞，直到上面zookeeper 连接上为止
            countDownLatch.await();
            // zookeeper创建成功，创建锁节点
            Stat exists = zooKeeper.exists(lockRootName, false);
            if(null == exists ){
                zooKeeper.create(lockRootName,bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * zookeeper 分布式锁： 加锁
     */
    public void lock() throws InterruptedException {
        // 创建根节点
        try {
            String myNode = zooKeeper.create(lockRootName + "/" + lockName, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            List<String> children = zooKeeper.getChildren(lockRootName, false);
            // 排序，最小的一直放在最上面
            TreeSet<String> nodeSet = new TreeSet<>();
            for (String child : children) {
                nodeSet.add(lockRootName + "/" + child);
            }
            // 从拍好的数据中读取第一个数据，即最小的数据
            String minNode = nodeSet.first();
            // 前一个节点
            String preNode = nodeSet.lower(myNode);
            if(myNode.equals(minNode)) {
                currentLockName =  myNode;
                return;
            }
            // 剩余其他的进程进来之后都没有拿到分布式锁，因为他创建的节点不是当前最小的，这时候就要监听他前一个节点，看
            // 是否有删除时间，如果有，这个节点变成当前锁节点
            CountDownLatch countDownLatch = new CountDownLatch(1);
            if(null != preNode) {
                Stat stat = zooKeeper.exists(preNode, new MyZookeeperWatcher(countDownLatch));
                if(null != stat) {
                    // 阻塞进程，直到获取监听到节点删除事件
                    countDownLatch.await();
                    currentLockName = myNode;
                    countDownLatch = null;
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解锁，解锁的目的就是删除节点
     */
    public void unLock() {
        try {
            if( null != currentLockName ) {
                zooKeeper.delete(currentLockName,-1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }


    }


}
