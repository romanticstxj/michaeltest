package com.michael.designpattern.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Subject extends Observable{
	
	private String name;

	public Subject(String name) {
		super();
		this.name = name;
	}
	
	public synchronized void setChanged() {
        super.setChanged();
    }
	
}
