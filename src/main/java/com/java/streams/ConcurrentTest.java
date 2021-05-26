package com.java.streams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

public class ConcurrentTest {
	
	private static List<Integer> list1 = new ArrayList<>();
	private static List<Integer> list2 = Collections.synchronizedList(new ArrayList<>());
//	private static List<Integer> list2 = new ArrayList<>();
	private static List<Integer> list3 = new ArrayList<>();
	private static Lock lock = new ReentrantLock();

	public static void main(String[] args) {

//		testConcurrencySecurity();
		System.out.println(sequentialSum(100));
	}
	
	private static void test1() {
		long startTime=System.currentTimeMillis();   //获取开始时间
	    LongStream.rangeClosed(1L, 10000L)
	     .parallel()
	    .forEach(in->{
	      Thread thread = Thread.currentThread();
	      System.out.println("thread: "+thread.getName()+", value: "+in);
	      try{
	        Thread.sleep(5);
	      }catch (Exception ex){
	        ex.printStackTrace();
	      }
	    });
	    long endTime=System.currentTimeMillis(); //获取结束时间
	    System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
	}
	
	private static void test2() {
		List<String> list = Lists.newArrayList("michael", "test");
		Map<Integer, List<String>> map = list.stream().parallel().collect(Collectors.groupingByConcurrent(String::length));
	}
	
	private static void testConcurrencySecurity() {
		IntStream.range(0, 10000).forEach(list1::add);
	    IntStream.range(0, 10000).parallel().forEach(list2::add);
	    IntStream.range(0, 10000).parallel().forEach(i -> {
	    lock.lock();
	    try {
	        list3.add(i);
	    }finally {
	        lock.unlock();
	    }
	    });

	    System.out.println("串行执行的大小：" + list1.size());
	    System.out.println("并行执行的大小：" + list2.size());
	    System.out.println("加锁并行执行的大小：" + list3.size());
	}
	
	private static long sequentialSum(long n) {
		return Stream.iterate(1L, i->i+1)
				.limit(n)
				.reduce(0L, Long::sum);
	}

}
