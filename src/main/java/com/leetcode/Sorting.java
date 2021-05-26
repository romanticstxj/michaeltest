package com.leetcode;

import java.util.PriorityQueue;

public class Sorting {

	public static void main(String[] args) {
		int result = findKthLargest(new int[] {1,3,2,6,4,5,7}, 2);
		System.out.println(result);
	}
	
	public static int findKthLargest(int[] nums, int k) {
	    PriorityQueue<Integer> pq = new PriorityQueue<>();
	    for(int val : nums) {
	        pq.offer(val);
	        if(pq.size() > k) {
	            pq.poll();
	        }
	    }
	    return pq.peek();
	}

}
