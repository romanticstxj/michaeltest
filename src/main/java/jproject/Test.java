package jproject;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public static final String str = "test";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		while(true) {
			list.add(str);
			try{
				Thread.sleep(100);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
