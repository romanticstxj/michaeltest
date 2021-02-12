package com.leetcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public static final int INITIAL_READ_SIZE = 64;
	public static final char quotechar = '"';
	public static final char separator = ',';
	
	public static boolean hasNext = true;
	private static String[] strs;
	private static int blockIndex = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Test test = new Test("a,\"dddd\"\"b\"\"ddddd\",c\nd,e,f");
		System.out.println(parse("a,\"dddd\"\"b\"\"ddddd\",c\nd,e,f", ',', '"'));
	}
	
	public static ArrayList<ArrayList<String>> parse(String input, char separator, char quote) {
		ArrayList<ArrayList<String>> allElements = new ArrayList<>();
		strs = input.split("\n");
        while (blockIndex < strs.length) {
        	ArrayList<String> nextLineAsTokens = readNext();
            if (nextLineAsTokens != null)
                allElements.add(nextLineAsTokens);
        }
        return allElements;
    }

	public static ArrayList<String> readNext() {

        String nextLine = getNextLine();
        return parseLine(nextLine);
    }
	
	private static String getNextLine() {
        String nextLine = strs[blockIndex++];
        return nextLine;
    }
	
	private static ArrayList<String> parseLine(String nextLine)  {

        if (nextLine == null) {
            return null;
        }

        ArrayList<String> tokensOnThisLine = new ArrayList<String>();
        StringBuilder sb = new StringBuilder(INITIAL_READ_SIZE);
        boolean inQuotes = false;
        do {
            if (inQuotes) {
                // continuing a quoted section, reappend newline
                sb.append("\n");
                nextLine = getNextLine();
                if (nextLine == null)
                    break;
            }
            for (int i = 0; i < nextLine.length(); i++) {

                char c = nextLine.charAt(i);
                if (c == quotechar) {
                    if( isEscapedQuote(nextLine, inQuotes, i) ){ 
                        sb.append(nextLine.charAt(i+1));
                        i++;
                    }else{
                        inQuotes = !inQuotes;
                        // the tricky case of an embedded quote in the middle: a,bc"d"ef,g
                        if(i>2 //not on the beginning of the line
                                && nextLine.charAt(i-1) != separator //not at the beginning of an escape sequence 
                                && nextLine.length()>(i+1) &&
                                nextLine.charAt(i+1) != separator //not at the end of an escape sequence
                        ){
                            sb.append(c);
                        }
                    }
                } else if (c == separator && !inQuotes) {
                    tokensOnThisLine.add(sb.toString());
                    sb = new StringBuilder(INITIAL_READ_SIZE); // start work on next token
                } else {
                    sb.append(c);
                }
            }
        } while (inQuotes);
        tokensOnThisLine.add(sb.toString());
        return tokensOnThisLine;

    }

    private static boolean isEscapedQuote(String nextLine, boolean inQuotes, int i) {
        return inQuotes  // we are in quotes, therefore there can be escaped quotes in here.
            && nextLine.length() > (i+1)  // there is indeed another character to check.
            && nextLine.charAt(i+1) == quotechar;
    }
    
}
