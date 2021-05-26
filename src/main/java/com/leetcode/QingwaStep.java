package com.leetcode;

/**
 * 青蛙跳台阶问题:一只青蛙一次可以跳上 1 级台阶，也可以跳上 2 级台阶。求该青蛙跳上一个 n  级的台阶总共有多少种跳法。
 * 
 * 解法
	青蛙想上第 n 级台阶，可从第 n-1 级台阶跳一级上去，也可从第 n-2 级台阶跳两级上去，即：f(n) = f(n-1) + f(n-2)。递推求解即可。
 * @author tengx009
 *
 */
public class QingwaStep {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(numWays2(7));
	}

	public static int numWays(int n) {
        int a = 0, b = 1;
        for (int i = 0; i < n; ++i) {
            int s = (a + b) % 1000000007;
            a = b;
            b = s;
        }
        return b;
    }
	
	public static int numWays2(int n) {
        if(n==1) 
        	return 1;
        if(n ==2)
        	return 2;
        return numWays2(n-1) + numWays2(n-2);
    }
}
