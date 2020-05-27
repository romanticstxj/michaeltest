package com.michael.designpattern.singleton.privatestatic;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AliPayConfig config1 = new AliPayConfig();
		AliPayConfig config2 = new AliPayConfig();
		
		AlipayClient alipayClient1 = config1.getAlipayClient();
		AlipayClient alipayClient2 = config2.getAlipayClient();
		
		System.out.println(alipayClient1 == alipayClient2);
	}

}
