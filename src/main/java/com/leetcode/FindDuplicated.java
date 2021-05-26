package com.leetcode;

import java.util.HashMap;
import java.util.Map;

public class FindDuplicated {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<Integer, Integer> result = findRepeatNumber1(new int[] {1,2,3,4,3,5});
		System.out.println(result);
	}
	
//	/**
//	 * 1,2,3,4,3,5
//	 * @param numbers
//	 * @return
//	 */
//	public static int find(int[] numbers) {
//		int[] arr = new int[];
//		for(int i=0;i<numbers.length;i++) {
//			if(numbers[i] != i) {
//				
//			}
//			numbers[numbers[i]]
//		}
//	}
//	
	public static int findRepeatNumber(int[] nums) {
        for (int i = 0, n = nums.length; i < n; ++i) {
            while (nums[i] != i) {
                if (nums[i] == nums[nums[i]]) return nums[i];
                swap(nums, i, nums[i]);
            }
        }
        return -1;
    }
	
	public static Map<Integer, Integer> findRepeatNumber1(int[] nums) {
		Map<Integer, Integer> map = new HashMap<>();
        for (int num: nums) {
            if(map.containsKey(num)) {
            	map.put(num, map.get(num)+1);
            } else {
            	map.put(num, 1);
            }
        }
        return map;
    }

    private static void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

}
