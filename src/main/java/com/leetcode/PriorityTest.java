package com.leetcode;

import java.util.PriorityQueue;

public class PriorityTest {

	public static void main(String[] args) {
		for(int i: getLeastKs(new int[] {1,2,6,4,3,2,8}, 4)) {
			System.out.println(i);
		}
	}
	
	/**
	 * 1,2,6,4,3,2,8
	 * 4
	 * 1,2,2,3
	 * @param arr
	 * @return
	 */
	public static int[] getLeastKs(int[] arr, int k) {
		PriorityQueue<Integer> q = new PriorityQueue<>();
		for(int i: arr) {
			q.offer(i);
		}
		int[] result = new int[k];
		int i=0;
		do {
			result[i] = q.poll();
		} while(++i<k);
		return result;
	}

}
