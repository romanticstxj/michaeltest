package com.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoSum {

	public static void main(String[] args) {
		System.out.println(twoSum(new int[] {1,2,7,3,6}, 9));
	} 
	
	public static List<String> twoSum(int[] arr, int sum){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		List<String> result = new ArrayList<>();
		for(int i: arr) {
			if(map.containsKey(i)) {
				result.add(i + "+" + map.get(i) + "=" + sum);
			} else {
				map.put(sum - i, i);
			}
		}
		return result;
	}

}
