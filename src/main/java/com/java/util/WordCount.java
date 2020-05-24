/***********************************************************************************************************************
 * FileName - WordCount.java
 * 
 * (c) Disney. All rights reserved.
 * 
 * $Author: tengx009 $ 
 * $Revision: #1 $ 
 * $Change: 715510 $ 
 * $Date: May 20, 2019 $
 **********************************************************************************************************************/
package com.java.util;

import java.util.Arrays;

public class WordCount {
    
    private static final char[] cardinals = new char[] {'a', 'e', 'i', 'o', 'u'};

    public static void main(String[] args) {
        String test = "I have a dream, which can make me crazy.";
        System.out.println(countCardinal(test));
    }
    
    public static int countCardinal(String str) {
        int count = 0;
        for(char c: str.toCharArray()) {
            int index = Arrays.binarySearch(cardinals, Character.toLowerCase(c));
            System.out.println("index: " + index + ", char: " + c);
            if(index >= 0) {
                count++;
            }
        }
        return count;
    }

}
