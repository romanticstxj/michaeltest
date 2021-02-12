package com.java.multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.common.collect.Lists;

public class ArrayListConcurrentTest {
	
	private static int TIMES = 100;

	public static void main(String[] args) {
		testConcurrentLinkedDeque();
	}
	
	public static void testArrayList() {
		List<String> list = new ArrayList<>(2000);
		
		
		for(int i=0;i<10;i++) {
			new Thread(()-> {
				for(int j=0; j<TIMES; j++) {
					list.add("test");
				}
			}).start();
		}
		
		System.out.println(list.size());
	}
	
	/**
	 * 测试ConcurrentLinkedDeque是否线程安全
	 * 1）多线程插入数据到此集合
	 */
	public static void testConcurrentLinkedDeque() {
		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList();
		
		
		for(int i=0;i<10;i++) {
			new Thread(()-> {
				for(int j=0; j<TIMES; j++) {
					list.add("test");
				}
			}).start();
		}
		
		System.out.println(list.size());
	}

}
