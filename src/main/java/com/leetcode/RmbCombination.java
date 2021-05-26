package com.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;

import com.google.common.collect.Comparators;
import com.google.common.collect.Lists;

/**
 * 给定1、5、10、20、50元的情况下，问100元可以拆分为多少种组合方式
 * @author tengx009
 *
 */
public class RmbCombination {
	
	private static Integer[] units = new Integer[] {1,5,10,20,50};
	private static long MOD = 1000000007;
	

	public static void main(String[] args) {
		long result=GetPartitionCount(3, 3);
		System.out.println(result);
	}
	
	public static ArrayList<ArrayList<Integer>> f(int number) {
		if(number == 1) {
			ArrayList<ArrayList<Integer>> list = new ArrayList<>();
			list.add(Lists.newArrayList(1));
			return list;
		} else {
			int number_min = 0;
			return union(f(number_min), f(number - number_min));
		}
	}
	
	/**
	 * ArrayList<ArrayList<Integer>> 里面的list代表一种方案如1，1，1，1，1
	 * 外面list代表有几种方案
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static ArrayList<ArrayList<Integer>> union(ArrayList<ArrayList<Integer>> list1, ArrayList<ArrayList<Integer>> list2) {
		Assert.assertNotNull(list1);
		Assert.assertNotNull(list2);
		list2.forEach(ele -> list1.add(ele));
		return list1;
	}
	
	public static long GetPartitionCount(int n, int max)
	{
	    if (n == 1 || max == 1) {
	    	return 1;
	    }
	    if (n < max) {
	    	return (GetPartitionCount(n, n)) % MOD;
	    }
	    if (n == max) {
	    	return (1 + GetPartitionCount(n, n - 1)) % MOD;
	    }  else {
	    	return (GetPartitionCount(n - max, max) + GetPartitionCount(n, max - 1)) % MOD;
	    }
	}

}
