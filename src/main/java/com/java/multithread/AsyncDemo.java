package com.java.multithread;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncDemo {
	
	private int i=0;
    private static void doSomeTask() {
        System.out.println("Hello World");
    }
    private static void onCompletion() {
        System.out.println("All tasks finished");
    }
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        final CountDownLatch latch = new CountDownLatch(2);
        System.out.println(" caller thread is: " + Thread.currentThread());
        executor.execute(new Task(latch));
        executor.execute(new Task(latch));
        executor.execute(() -> {
            try {
            	System.out.println(" caller thread before latch await complete is: " + Thread.currentThread());
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onCompletion();
        });
        executor.shutdown();
    }
    private static class Task implements Runnable {
        /**
         * CountDownLatch ��JDK�ṩ��һ���򵥵��̼߳�⹤��
         * ���ڼ򵥵ļ���������countDown()����������ǰ�߳��Ѿ���ֹ
         * �ڼ���߳��е���await()����,�÷�����һֱ����ֱ�����������߳���ֹ
         */
    	private int j=0;
        private final CountDownLatch latch;
        public Task(CountDownLatch latch) {
            this.latch = latch;
        }
        @Override
        public void run() {
            try {
            	System.out.println(" caller thread is: " + Thread.currentThread());
                doSomeTask();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }
}