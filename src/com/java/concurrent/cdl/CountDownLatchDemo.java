package com.java.concurrent.cdl;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {
	private static int SIZE = 100;
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		//All must share a single Countdownlatch object
		CountDownLatch latch = new CountDownLatch(SIZE);
		for(int i=0; i<10; i++){
			exec.execute(new WaitingTask(latch));
		}
		for(int i=0; i<SIZE-1; i++){
			exec.execute(new TaskPortion(latch));
		}
		System.out.println("launched all tasks");
		
		latch.await();
		System.out.println("main thread await finished");
		exec.shutdown();
	}

}

//Performs some portion of a task
class TaskPortion implements Runnable{
	private static int counter = 0;
	private final int id = counter++;
	private static Random rand = new Random(47);
	private final CountDownLatch latch;
	public TaskPortion(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			doWork();
			latch.countDown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void doWork() throws InterruptedException{
		TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
		System.out.println(this + "completed");
	}

	@Override
	public String toString() {
		return String.format("%1$-3d ", id);
	}
}

//Waits on the CountDownLatch
class WaitingTask implements Runnable{
	private static int counter = 0;
	private final int id = counter++;
	private final CountDownLatch latch;
	public WaitingTask(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			latch.await();
			System.out.println("Latch barrier passed for " + this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return String.format("Waiting task %1$-3d ", id);
	}
}
