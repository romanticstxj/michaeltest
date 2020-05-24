package com.java.multithread;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/***********************************************************************************************************************
 * FileName - AtomicReferenceTest.java
 * 
 * (c) Disney. All rights reserved.
 * 
 * $Author: tengx009 $ 
 * $Revision: #1 $ 
 * $Change: 715510 $ 
 * $Date: Aug 20, 2019 $
 **********************************************************************************************************************/

public class AtomicReferenceTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
     // 创建两个Person对象，它们的id分别是101和102。
        Person p1 = new Person(101);
        Person p2 = new Person(102);
        // 新建AtomicReference对象，初始化它的值为p1对象
        AtomicReference ar = new AtomicReference(p1);
        //异步更改p1的id.
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(new MyTask(p1));
        es.shutdown();
        Person p3 = (Person)ar.get();
        System.out.println("p3 is "+p3);
        // 通过CAS设置ar。如果ar的值为p1的话，则将其设置为p2。
        ar.compareAndSet(p1, p2);
        
        p3 = (Person)ar.get();
        System.out.println("p3 is "+p3);
    }
    
    static class MyTask implements Runnable{
		
    	private Person p;
    	public MyTask(Person p) {
    		this.p = p;
    	}
		
		@Override
		public void run() {
			this.p.setType(106);
		}
	}
}
