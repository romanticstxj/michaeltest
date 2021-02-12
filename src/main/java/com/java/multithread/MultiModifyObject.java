package com.java.multithread;

public class MultiModifyObject {
	
	public String name;

	public static void main(String[] args) {
		MultiModifyObject object = new MultiModifyObject();
		
		new Thread(()-> {
			object.name="";
		}).start();  
	}

}
