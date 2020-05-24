package com.java.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hobby h = new Hobby("fishing");
		Person p1 = new Person("Michael", h);
		Person p2 = new Person("Michael", h);
		List<Person> list = new ArrayList<>();
		list.add(p1);
		list.add(p2);
		
		List<Person> list2 = new ArrayList<>();
		list2.add(p1);
		list2.add(p2);
		
		boolean b = Objects.equals(list, list2);
		System.out.println(b);
	}

}
