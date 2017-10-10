package com.java.concurrent.cdl;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class HorseRace {

	public static void main(String[] args) {

	}

}

class Horse implements Runnable{
	private static int counter = 0;
	private final int id = counter++;
	private int strides = 0;
	private static Random rand = new Random(47);
	private static CyclicBarrier barrier;
	public Horse(CyclicBarrier barrier) {
		this.barrier = barrier;
	}
	
	public synchronized int getStrides(){
		return strides;
	}

	@Override
	public void run() {
		while(!Thread.interrupted()){
			synchronized (this) {
				strides += rand.nextInt(3);
			}
			barrier.await();
		}
	}
	
}
