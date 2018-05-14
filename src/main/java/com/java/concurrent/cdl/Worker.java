package com.java.concurrent.cdl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Worker implements Runnable {  
    private final CountDownLatch startSignal;  
    private final CountDownLatch doneSignal;  
    Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {  
        this.startSignal = startSignal;  
        this.doneSignal = doneSignal;  
    }  
    public void run() {  
        try {  
//        	System.out.println("begin");
        	System.out.println(Thread.currentThread() + " startSignal await");
            startSignal.await();  
            System.out.println(Thread.currentThread() + " startSignal await Finished");
            doWork();  
            System.out.println(Thread.currentThread() + " doneSignal countdown");
            doneSignal.countDown();  
        } catch (InterruptedException ex) {} // return;  
    }  
 
    void doWork() { 
    	try {
    		System.out.println("doing work");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }  

}
