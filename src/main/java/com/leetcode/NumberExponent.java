package com.leetcode;

/**
 * 数值的整数次方
 * @author tengx009
 *
 */
public class NumberExponent {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(myPow(3.0, 2));
	}

	public static double myPow(double x, int n) {
        if (n == 0) return 1;
        if (n == 1) return x;
        if (n == -1) return 1 / x;
        double half = myPow(x, n / 2);
        return half * half * myPow(x, n % 2);
    }
}
