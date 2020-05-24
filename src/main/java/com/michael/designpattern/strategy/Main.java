package com.michael.designpattern.strategy;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlusCalculate pc = new PlusCalculate();
		CalculateManage cm = new CalculateManage(pc);
		System.out.println(cm.calculate(1, 3));
		
		MinusCalculate mc = new MinusCalculate();
		cm = new CalculateManage(mc);
		System.out.println(cm.calculate(1, 3));
	}

}
