package com.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Assert;

public class Challenge {
	
	private static final String M[] = { "", "M", "MM", "MMM" };
	private static final String C[] = { "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" };
	private static final String X[] = { "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" };
	private static final String I[] = { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" };
	
	private static final String template = "<h_hNum_>_content_</h_hNum_>";
	
	private static final char ENTER = '\n';

	public static void main(String[] args) {
//		String roman = intToRoman(1);
//		//MDCCC, MLXXX
//		System.out.println(roman);
		
//		String markDown = markdownParser("    ######  ");
//		System.out.println(markDown);
		
//		test();
		System.out.println("d\"ddddd\"");
		System.out.println(parse("1\t2\t3\n4\t5\t6", '\t', '\"'));
	}
	
	public static String intToRoman(int n) {
		if(n >= 4000 || n<=0) {
			return "INVALID";
		}
       return M[n / 1000] + C[(n % 1000) / 100] + X[(n % 100) / 10] + I[n % 10];
   }
	
	/**
	 * 1) \"
	 * @param markdown
	 * @return
	 */
	public static String markdownParser(String markdown) {
		if(markdown == null) {
			return "";
		}
		String trimedMarkdown = markdown.trim();
		int centerIndex = trimedMarkdown.indexOf(" ");
		if(-1 == centerIndex) {
			return trimedMarkdown;
		}
        String hNumReg = trimedMarkdown.substring(0, centerIndex).trim();
        String content = trimedMarkdown.substring(centerIndex+1).trim();
        if(!allCharsOf(hNumReg, '#')){
        	return trimedMarkdown;
        }

        int hNum= hNumReg.length();
        if(hNum > 6) {
        	return trimedMarkdown;
        }
        return template.replaceAll("_hNum_", String.valueOf(hNum)).replaceAll("_content_", content);
	}
	
	public static boolean allCharsOf(String s, char c) {
		return s.chars().allMatch(sc -> sc==c);
	}
	
	public static ArrayList<ArrayList<String>> parse(String input, char separator, char quote) {
		Assert.assertNotNull(separator);
		Assert.assertNotNull(quote);
		
		ArrayList<ArrayList<String>> csvData = new ArrayList<>();
		ArrayList<String> row = new ArrayList<>();
		csvData.add(row);
		if(input == null) {
			return csvData;
		}
		
		char[] chars = input.toCharArray();
		LinkedList<Integer> quoteIndexes = new LinkedList<Integer>();
		int pos = -1;
		for(int i=0;i<chars.length;i++) {
			if(quoteIndexes.isEmpty()) {
				//outside of quote, normal case
				if(chars[i] == quote) {
					quoteIndexes.push(i);
				} else if(chars[i] == separator) {
					//only when separator is outside of quote, this separator can be recognized as real separator
					String cell = input.substring(pos+1, i);
					row.add(cell);
					pos = i;
				} else if(chars[i] == ENTER) {
					//only when \n is outside of quote, this \n can be recognized as real \n
					String cell = input.substring(pos+1, i);
					row.add(cell);
					pos = i;
					
					row = new ArrayList<>();
					csvData.add(row);
				}
			} else {
				//inside quote, ignore key words, treat as plain text, wait until quote stack pops
				if(chars[i] == quote) {
					quoteIndexes.pop();
				}
			}
		}
		String cell = input.substring(pos+1);
		row.add(cell);
		
		//quote should be couples
		if(quoteIndexes.size() % 2 != 0) {
			throw new RuntimeException("input is incorrect for quote");
		}
        return csvData;
    }
}
