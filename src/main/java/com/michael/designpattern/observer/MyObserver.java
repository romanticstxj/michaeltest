package com.michael.designpattern.observer;

import java.util.Observable;
import java.util.Observer;

public class MyObserver implements Observer{
	
	public int id;
	

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println(arg);
	}
}
