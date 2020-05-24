package com.michael.designpattern.adapter;

public class DriveToFlyObjectAdapter implements IFly{
	
	private IDrive drive;
	
	public DriveToFlyObjectAdapter(IDrive drive) {
		setDrive(drive);
	}

	public void setDrive(IDrive drive) {
		this.drive = drive;
	}

	@Override
	public void fly() {
		// TODO Auto-generated method stub
		drive.drive();
		System.out.println("DriveToFlyObjectAdapter fly");
	}

}
