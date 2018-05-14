package com.michael.designpattern.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class MainClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Observer ob1 = new MyObserver();
		Observer ob2 = new MyObserver();
		Observer ob3 = new MyObserver();
		List<Observer> list = new ArrayList();
		list.add(ob1);
		list.add(ob2);
		list.add(ob3);
		Subject sub = new Subject("system start");
		sub.addObserver(ob1);
		sub.addObserver(ob3);
		sub.addObserver(ob2);
		Subject sub1 = new Subject("system end");
		
		List<Subject> subs = new ArrayList();
		subs.add(sub);
		subs.add(sub1);
		Container container = new Container(subs);
		container.start();
	}

}
