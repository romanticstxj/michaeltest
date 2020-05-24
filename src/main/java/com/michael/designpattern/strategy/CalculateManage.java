package com.michael.designpattern.strategy;

public class CalculateManage {

	private ICalculate<Integer> calculate;
	
	public CalculateManage(ICalculate<Integer> calculate) {
		setCalculate(calculate);
	}

	public void setCalculate(ICalculate<Integer> calculate) {
		this.calculate = calculate;
	}
	
	public Integer calculate(Integer a, Integer b) {
		return calculate.calculate(a, b);
	}
	
}
