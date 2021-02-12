package com.java.multithread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CommunicationWithThreads {

	public static void main(String[] args) {
//		runABCWhenAllReady();
		doTaskWithResultInWorker();
	}
	
	private static void runABCWhenAllReady() {
	    int runner = 4;
	    CyclicBarrier cyclicBarrier = new CyclicBarrier(runner);
	    final Random random = new Random();
	    for (char runnerName='A'; runnerName <= 'C'; runnerName++) {
	        final String rN = String.valueOf(runnerName);
	        new Thread(new Runnable() {
	            @Override
	            public void run() {
	                long prepareTime = random.nextInt(10000) + 100;
	                System.out.println(rN + " is preparing for time:" + prepareTime);
	                try {
	                    Thread.sleep(prepareTime);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                try {
	                    System.out.println(rN + " has prepared, waiting for others");
	                    cyclicBarrier.await(); // 当前运动员准备完毕，等待别人准备好
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                } catch (BrokenBarrierException e) {
	                    e.printStackTrace();
	                }
	                System.out.println(rN + " starts running"); // 所有运动员都准备好了，一起开始跑
	            }
	        }).start();
	    }
	}
	
	private static void doTaskWithResultInWorker() {
	    Callable<Integer> callable = new Callable<Integer>() {
	        @Override
	        public Integer call() throws Exception {
	            System.out.println(Thread.currentThread() + " Task starts");
	            Thread.sleep(1000);
	            int result = 0;
	            for (int i=0; i<=100; i++) {
	                result += i;
	            }
	            System.out.println(Thread.currentThread() + " Task finished and return result");
	            return result;
	        }
	    };
	    FutureTask<Integer> futureTask = new FutureTask<>(callable);
	    new Thread(futureTask).start();
	    
	    new Thread(() -> {
	    	try {
		        System.out.println(Thread.currentThread() + " Before futureTask.get()");
		        System.out.println(Thread.currentThread() + " Result:" + futureTask.get());
		        System.out.println(Thread.currentThread() + " After futureTask.get()");
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    } catch (ExecutionException e) {
		        e.printStackTrace();
		    }
	    }).start();
//	    try {
//	        System.out.println("Before futureTask.get()");
//	        System.out.println("Result:" + futureTask.get());
//	        System.out.println("After futureTask.get()");
//	    } catch (InterruptedException e) {
//	        e.printStackTrace();
//	    } catch (ExecutionException e) {
//	        e.printStackTrace();
//	    }
	}

}
