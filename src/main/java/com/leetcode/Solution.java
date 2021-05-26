package com.leetcode;

public class Solution {

	public static void main(String[] args) {
		System.out.println(findRepeatNumber(new int[] {2, 3, 1, 0, 2, 5, 3}));
	}

	public static int findRepeatNumber(int[] nums) {
        for (int i = 0, n = nums.length; i < n; ++i) {
            while (nums[i] != i) {
                if (nums[i] == nums[nums[i]]) return nums[i];
                swap(nums, i, nums[i]);
            }
        }
        return -1;
    }

    private static void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
