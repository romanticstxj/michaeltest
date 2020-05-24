package com.michael.designpattern.adapter;

public class Main {

	public static void main(String[] args) {
		DriveToFlyAdapter adapter1 = new DriveToFlyAdapter();
		adapter1.fly();
		
		IDrive drive = new Car();
		DriveToFlyObjectAdapter adapter2 = new DriveToFlyObjectAdapter(drive);
		adapter2.fly();
		
	}
}
