package com.michael.designpattern.strategy;

public class MinusCalculate implements ICalculate<Integer>{

	@Override
	public Integer calculate(Integer a, Integer b) {
		// TODO Auto-generated method stub
		return a - b;
	}

}
