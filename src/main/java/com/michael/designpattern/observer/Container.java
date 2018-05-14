package com.michael.designpattern.observer;

import java.util.List;

public class Container {
	
	private List<Subject> subjects;
	
	public Container(List<Subject> subjects){
		this.subjects = subjects;
	}
	
	public void start(){
		for(Subject sub: subjects){
			sub.setChanged();
			sub.notifyObservers("hello");
		}
	}
	
	public void end(){
		
	}
	
}
