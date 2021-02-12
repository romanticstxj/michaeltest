package com.java.streams;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

public class CollectTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> list = Lists.newArrayList("michael", "test");
//		String result = list.stream().collect(Collectors.collectingAndThen(Collectors.joining(","), String::toUpperCase));
//		System.out.println(result);
//		
//		
//		List<User> list2 = Lists.newArrayList(new User("michael", 100, 1), new User("Test1", 30, 2), new User("Test2", 30, 1));
//		Map<Integer, List<String>> map = list.stream().collect(Collectors.groupingBy(String::length));
//		Map<Integer, List<User>> map2 = list2.stream().collect(Collectors.groupingBy(User::getAge));
//		
//		Map<Integer, Set<User>> map3 = list2.stream().collect(Collectors.groupingBy(User::getPosition, Collectors.toSet()));
//		System.out.println(map3);
//		
		
		Optional<String> min = list.stream().collect(Collectors.minBy(Comparator.comparingInt(String::length)));
		System.out.println(min);
	}
	
	private static void test() {
		
	}

}
