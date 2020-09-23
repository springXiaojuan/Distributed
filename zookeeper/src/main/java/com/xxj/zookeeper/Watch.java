package com.xxj.zookeeper;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;


/**
 * @author  xuxiaojuan
 * @date  2020/8/24 5:42 下午
 */
@Log4j
public class Watch {
    public static String ZK_NODE = "/root";
    public static void main(String[] args) throws Exception {
        String ZK_ADDRESS = "127.0.0.1:2181";

        RetryPolicy policy = new ExponentialBackoffRetry(1000, 2);

        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(ZK_ADDRESS, policy);

        curatorFramework.start();

        Stat stat = curatorFramework.checkExists().forPath(ZK_NODE);
        if(stat == null) {
            String s = curatorFramework.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(ZK_NODE,"sssss".getBytes());
            System.out.println(" ============ " + s);
        }
//         此操作只监听第一次操作，其余的操作不受控制
//        curatorFramework.getData().usingWatcher((CuratorWatcher) (WatchedEvent var1) -> {
//            System.out.println("对节点" + var1.getPath()+"进行了操作：" + var1.getType().name());
//        }).forPath(ZK_NODE);

//        curatorWatch(curatorFramework);
        // 只监听子节点信息,子节点的子节点信息是不监听的
//        curatorWatchPathChildren(curatorFramework);
        treeNode(curatorFramework);
        Thread.sleep(1000000000);
    }

    // 设置监听模式 这个监听模式可以监听节点及其子节点，all time
    public static void curatorWatch(CuratorFramework client){
        CuratorCache curatorCache = CuratorCache.build(client,ZK_NODE);
        CuratorCacheListener listener = CuratorCacheListener.builder().forCreates(node -> System.out.println(String.format("Node created:[%s]", node)))
                .forChanges((oldNode, node) -> {
                    System.out.println(String.format("Node changed. old:[%s] new :[%s]", oldNode, node));
                })
                .forDeletes(oldNode -> System.out.println(String.format("Node deleted.oldValue : [%s]", oldNode)))
                .forInitialized(() -> System.out.println("Cache Initialiized"))
                .build();

        curatorCache.listenable().addListener(listener);
        curatorCache.start();
    }

    public static void curatorWatchPathChildren(CuratorFramework client){
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, ZK_NODE, true);
        System.out.println("listening start ......");
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch(event.getType()) {
                    case CHILD_ADDED:
                        System.out.println( event.getData().getPath() + " children added");
                        break;
                    case CHILD_UPDATED:
                        System.out.println(event.getData().getPath() + " children updated");
                        break;
                    case CHILD_REMOVED:
                        System.out.println(event.getData().getPath() + " children removed");
                        break;
                    case INITIALIZED:
                        System.out.println(event.getData().getPath() + " children initialized");
                        break;
                    case CONNECTION_LOST:
                        System.out.println(event.getData().getPath() + " children connection lost");
                        break;
                    case CONNECTION_SUSPENDED:
                        System.out.println(event.getData().getPath() + "  children connection suspended");
                        break;
                    case CONNECTION_RECONNECTED:
                        System.out.println(event.getData().getPath() + " children connection reconnected");
                        break;
                }
            }
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        try {
            pathChildrenCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void treeNode(CuratorFramework client) {
        TreeCache treeCache = TreeCache.newBuilder(client, ZK_NODE).build();

        treeCache.getListenable().addListener((listen,event) -> {
            if(event.getData() == null) {
                System.out.println(event.getData().getPath() + " jjjj " +event.getType().name() );
            } else {
                System.out.println(event.getData().getPath() + " jjjj " +event.getType().name() );
            }
        });

        try {
            treeCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
