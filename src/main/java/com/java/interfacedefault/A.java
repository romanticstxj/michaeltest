package com.java.interfacedefault;

public interface A {

	default void test() {
		System.out.println("A");
	}
}
