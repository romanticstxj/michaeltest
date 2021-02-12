package com.michael.guava;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class StudyGuavaCache {

	public static void main(String[] args) throws InterruptedException {
		testExpireAfterWrite();
	}
	
	public static void testExpireAfterWrite() throws InterruptedException {
		Cache<String,String> cache = CacheBuilder.newBuilder()
                .maximumSize(2)
                .expireAfterWrite(3,TimeUnit.SECONDS) //设值后要不断更新，而且更新频率要在3秒以内，不然就在没有CU操作3秒后失效
                .build();
        cache.put("key1","value1");
        int time = 1;
        while(true) {
            System.out.println("第" + time + "次取到key1的值为：" + cache.getIfPresent("key1"));
            cache.put("key1", "value1" + time);
            Thread.sleep(time*1000);
        }
	}
	
	public static void testExpireAfterAccess() throws InterruptedException {
		Cache<String,String> cache = CacheBuilder.newBuilder()
                .maximumSize(2)
                .expireAfterAccess(3,TimeUnit.SECONDS) //设值后要不断访问或更新，而且访问频率要在3秒以内，不然就在没有CRU操作3秒后失效
                .build();
        cache.put("key1","value1");
        Thread.sleep(3000);
        int time = 1;
        while(true) {
            Thread.sleep(time*1000);
            System.out.println("睡眠" + time++ + "秒后取到key1的值为：" + cache.getIfPresent("key1"));
        }
	}

}
