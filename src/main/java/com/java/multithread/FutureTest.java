package com.java.multithread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		 Future future = executor.submit(new Callable<String>() {
		 
		 @Override
		 public String call() throws Exception {
		 //do some thing
		 Thread.sleep(100);
		 return "i am ok";
		 }
		 });
		 System.out.println(future.isDone());
		 System.out.println(future.get());
	}

}
