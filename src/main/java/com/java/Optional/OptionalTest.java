package com.java.Optional;

import java.util.Optional;

public class OptionalTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer i = new Integer(2);
		Optional.ofNullable(i).ifPresent(t -> t = 3);
		System.out.println(i);

		int j = 4;
		Optional.ofNullable(i).ifPresent(t -> t = 5);

//		User user = new User(1);
//		Optional.ofNullable(i).ifPresent(t -> t = new User(2));
	}

}
