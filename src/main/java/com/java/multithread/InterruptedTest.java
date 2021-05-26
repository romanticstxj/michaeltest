package com.java.multithread;

public class InterruptedTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
            	while(true) {
//            		try {
//                        // this makes sure quartz scheduler shutdown properly during server stopping
//                        Thread.sleep(30000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException("runtime ex");
//                    }
            	}
                
            }

        });
		t.setDaemon(true);
		t.start();
		
//		System.out.println("begin to interrupte: " + Thread.currentThread());
//		t.interrupt();
		
	}

}
