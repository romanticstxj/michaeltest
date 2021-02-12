package com.java.Optional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class OptionalTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String, String> map = Maps.newHashMap();
		String s  = null;
		map.putIfAbsent("test", s);
		System.out.println(map);
		
//		int i = 2;
//		Optional.ofNullable(i).ifPresent(t -> System.out.println(t));
//		System.out.println(i);
////
//		int j = 4;
//		Optional.ofNullable(i).ifPresent(t -> t = 5);
//
//		User user = new User(1);
//		Optional.ofNullable(i).ifPresent(t -> t = new User(2));

//		String s = null;
//		Optional<String> strOptional = Optional.ofNullable(s);
//		System.out.println(Thread.currentThread());
//		strOptional.ifPresentOrElse(x-> {
//			System.out.println(Thread.currentThread() + x);
//		}, () -> {
//			//此处并没生成新线程，而只是借用Runnable接口来表达Function
//			System.out.println(Thread.currentThread() + "Null");
//		});
//		String s = null;
//		String string = Optional.ofNullable(s).orElse("de");
//		System.out.println(string);
//		test1();
	}

	public static void test1() {
		List<User> userDOList = Lists.newArrayList(new User(1), new User(2), new User(3));
//		Map<Integer, List<User>> roleUserMap = new HashMap<>();
//		for (User userDO : userDOList) {
//			int roleId = userDO.getId();
//			List<User> userList = roleUserMap.get(roleId);
//			if (Objects.isNull(userList)) {
//				userList = new ArrayList<>();
//				roleUserMap.put(roleId, userList);
//			}
//			userList.add(userDO);
//		}
//		System.out.println(roleUserMap);
		
		Map<Integer, List<User>> roleUserMap = new HashMap<>();
		for (User userDO : userDOList) {
		 roleUserMap.computeIfAbsent(userDO.getId(), key -> new ArrayList<>())
		 .add(userDO);
		}
	}

}
