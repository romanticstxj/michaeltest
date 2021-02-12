package com.java.multithread;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import com.java.util.Hobby;
import com.java.util.Person;

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
        Person p1 = new Person("michael", new Hobby("football"));
        Person p2 = new Person("jason", new Hobby("basketball"));
        Person p4 = new Person("Hudson", new Hobby("volleyball"));
        // 新建AtomicReference对象，初始化它的值为p1对象
        AtomicReference ar = new AtomicReference(p1);
        //异步更改p1的id.
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(new MyTask1(ar, p2));
        es.shutdown();
        Person p3 = (Person)ar.get();
        //虽然异步线程把p1的内容改了，但是ar指向的p1的地址没变，所以可以设值成功，ar比较的事对象的地址，而不是对象的内容
        System.out.println("p1 is "+p1);
        System.out.println("p3 is "+p3);
        System.out.println("p2 is "+p2);
        // 通过CAS设置ar。如果ar的值为p1的话，则将其设置为p2。
        ar.compareAndSet(p1, p4);
//        ar.set(p4);
        
        p3 = (Person)ar.get();
        System.out.println("p1 is "+p1);
        System.out.println("p3 is "+p3);
        System.out.println("p2 is "+p2);
    }
    
    static class MyTask implements Runnable{
		
    	private Person p;
    	public MyTask(Person p) {
    		this.p = p;
    	}
		
		@Override
		public void run() {
			this.p.setName("peter");
		}
	}
    static class MyTask1 implements Runnable{
		
    	private AtomicReference ar;
    	private Person p;
    	public MyTask1(AtomicReference ar, Person p) {
    		this.ar = ar;
    		this.p = p;
    	}
		
		@Override
		public void run() {
			this.ar.set(p);
		}
	}
}
