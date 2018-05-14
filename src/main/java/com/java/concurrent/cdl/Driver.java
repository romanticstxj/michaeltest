package com.java.concurrent.cdl;

import java.util.concurrent.CountDownLatch;

public class Driver {
	public static void main(String[] args) throws InterruptedException {  
        CountDownLatch startSignal = new CountDownLatch(1);  
        CountDownLatch doneSignal = new CountDownLatch(5);  
 
        for (int i = 0; i < 5; ++i) // create and start threads  
            new Thread(new Worker(startSignal, doneSignal)).start();  
 
        doSomethingElse();            // don't let run yet  
//        Thread.sleep(10000);
        System.out.println(Thread.currentThread() + " startSignal countdown");
        startSignal.countDown();      // let all threads proceed  
        doSomethingElse();  
        System.out.println(Thread.currentThread() + " doneSignal await");
        doneSignal.await();           // wait for all to finish  
        System.out.println(Thread.currentThread() + " doneSignal await Finished");
    } 
	
	private static void doSomethingElse(){
		System.out.println("do something else");
	}
	
}
