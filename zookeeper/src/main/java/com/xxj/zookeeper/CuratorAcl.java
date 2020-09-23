package com.xxj.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  xuxiaojuan
 * @date  2020/8/29 10:53 上午
 */
public class CuratorAcl {

    private static String  ZK_NODE = "/root";
    public static void main(String[] args) throws Exception {

        RetryPolicy policy = new ExponentialBackoffRetry(1000,3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .authorization("digest","zhangsan:123456".getBytes())
                .connectString("127.0.0.1")
                .retryPolicy(policy)
                .connectionTimeoutMs(5000)
                .sessionTimeoutMs(5000)
                .build();


        curatorFramework.start();
//        createNode(curatorFramework);
//        updateNode(curatorFramework);
        getNodeData(curatorFramework);
        Thread.sleep(100000000);
    }

    public static void createNode(CuratorFramework curatorFramework) throws Exception {
        List<ACL>  aclList = new ArrayList<>();
        Id zhangsan = new Id("digest", DigestAuthenticationProvider.generateDigest("zhangsan:123456"));
        Id lisi = new Id("digest", DigestAuthenticationProvider.generateDigest("lisi:123456"));
        ACL acl = new ACL(ZooDefs.Perms.READ, zhangsan);
        ACL acl1 = new ACL(ZooDefs.Perms.ALL, lisi);
        aclList.add(acl1);
        aclList.add(acl);
        curatorFramework.create()
                .withMode(CreateMode.PERSISTENT)
                .withACL(aclList)
                .forPath(ZK_NODE);
    }


    public static void updateNode(CuratorFramework curatorFramework) throws Exception {
        Stat stat = curatorFramework.setData().forPath(ZK_NODE, "qw123".getBytes());
        System.out.println(stat.toString());
    }

    public static void getNodeData(CuratorFramework curatorFramework) throws Exception {
        String s = curatorFramework.getData().forPath(ZK_NODE).toString();
        System.out.println("node Data" + s);
    }
}
