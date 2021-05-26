package com.leetcode;

public class ErFenQuery {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] is = new int[] {1,2,3,4,5,6};
		System.out.println(new ErFenQuery().search(2, new int[] {1,2,3,4,5,6}));
		System.out.println(new ErFenQuery().search2(4, new int[] {1,2,3,4,5,6}, 0, is.length));
	}
	
	public int search(int key, int[] array) {
	    int l = 0, h = array.length - 1;
	    while (l <= h) {
	        int mid = l + (h - l) / 2;
	        if (key == array[mid]) return mid;
	        if (key < array[mid])  h = mid - 1;
	        else l = mid + 1;
	    }
	    return -1;
	}

	public int search2(int key, int[] array, int l, int h) {
		int mid = l + (h - l) / 2;
		if (key == array[mid]) 
			return mid;
		if (key < array[mid])  
			return search2(key, array, l, mid-1);
        else 
        	return search2(key, array, mid+1, h);
	}
}
