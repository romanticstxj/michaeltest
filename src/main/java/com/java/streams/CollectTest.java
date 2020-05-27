package com.java.streams;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

public class CollectTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> list = Lists.newArrayList("michael", "test");
		String result = list.stream().collect(Collectors.collectingAndThen(Collectors.joining(","), String::toUpperCase));
		System.out.println(result);
	}
	
	private static void test() {
		
	}

}
