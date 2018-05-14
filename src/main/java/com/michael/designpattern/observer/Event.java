package com.michael.designpattern.observer;

public class Event {
	
	private int type;
	private String name;
	
	
	public Event(String name, int type){
		this.name = name;
		this.type = type;
	}
}
