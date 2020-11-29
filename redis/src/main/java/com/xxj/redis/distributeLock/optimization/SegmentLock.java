package com.xxj.redis.distributeLock.optimization;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.xxj.redis.config.RedisConfig;
import com.xxj.redis.utils.ConvertArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author xuxiaojuan
 * @date 2020/11/26 6:05 下午
 */
@Component
public class SegmentLock {

    private static RedisTemplate redisTemplate ;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        SegmentLock.redisTemplate = redisTemplate;
    }

    public  String randomSegment(List<String> lockSegmentList) {
        String currentSegment = null;
        Set keys = redisTemplate.keys(ConvertArray.STORE_PREFIX + "*");
        Object[] randomKeys = keys.toArray();
        if(CollectionUtils.isEmpty(lockSegmentList)) {
            int i = new Random().nextInt(keys.size());
            // ex:  redis:store:0
            currentSegment = String.valueOf(randomKeys[i]);
            lockSegmentList.add(currentSegment);
        } else {
            keys.removeAll(lockSegmentList);
            randomKeys = keys.toArray();
            if(randomKeys.length <= 0) {
                int i = new Random().nextInt(lockSegmentList.size());
                currentSegment = String.valueOf(lockSegmentList.get(i));
            } else {
                String lastStoreKey = lockSegmentList.get(lockSegmentList.size() - 1);
                int i = new Random().nextInt(randomKeys.length);
                currentSegment = String.valueOf(randomKeys[i]);
                lockSegmentList.add(currentSegment);
            }
        }
        return currentSegment;
    }

}
