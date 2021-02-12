package com.java.util;

import java.math.BigDecimal;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String price = "264528.8";
        System.out.println(Float.parseFloat(price));
        BigDecimal parsePrice = new BigDecimal(String.format("%.2f", Float.parseFloat(price)));
        System.out.println(parsePrice);
        System.out.println(new BigDecimal(price));
        System.out.println("=======================");
        System.out.println(String.format("%.2f", 264528.8f));
        System.out.println(String.format("%.2f", 263458.8f));
        System.out.println(String.format("%.2f", 253458.8f));
        System.out.println(String.format("%.2f", 273450.8f));
        System.out.println(String.format("%.2f", 264528.7f));
        System.out.println(String.format("%.2f", 264528.3f));
        System.out.println(String.format("%.2f", 2402093.14f));
        System.out.println(String.format("%.2f", Float.parseFloat("2402093.14")));
	}

}
