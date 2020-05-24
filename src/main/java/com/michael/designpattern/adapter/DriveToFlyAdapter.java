package com.michael.designpattern.adapter;

public class DriveToFlyAdapter extends Car implements IFly{

	@Override
	public void fly() {
		// TODO Auto-generated method stub
		drive();
		System.out.println("adapter fly");
	}

}
