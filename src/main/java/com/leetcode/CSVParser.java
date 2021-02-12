package com.leetcode;

import java.util.ArrayList;


public class CSVParser {
	
	public static final int INITIAL_READ_SIZE = 64;
	public char quotechar = '"';
	public char separator = ',';
	
	public boolean hasNext = true;
	private String[] strs;
	private int blockIndex = 0;
	
  	public static void main(String[] args) throws Exception {
  		System.out.println(parse("a,\"dddd\"\"b\"\"ddddd\",c\nd,e,f", ',', '\"'));
  	}
  	
  	public CSVParser(String input, int blockIndex, boolean hasNext, char separator, char quote) {
  		this.strs = input.split("\n");
  		this.blockIndex = blockIndex;
  		this.hasNext = hasNext;
  		this.separator = separator;
  		this.quotechar = quote;
  	}
  	
  	public static ArrayList<ArrayList<String>> parse(String input, char separator, char quote) {
		CSVParser csvParser = new CSVParser(input, 0, true, separator, quote);
		return csvParser.doParse(input, separator, quote);
    }
  	
  	public ArrayList<ArrayList<String>> doParse(String input, char separator, char quote) {
  		ArrayList<ArrayList<String>> allElements = new ArrayList<>();
  		while (blockIndex < strs.length) {
        	ArrayList<String> nextLineAsTokens = readNext();
            if (nextLineAsTokens != null)
                allElements.add(nextLineAsTokens);
        }
        return allElements;
  	}

	public ArrayList<String> readNext() {

        String nextLine = getNextLine();
        return parseLine(nextLine);
    }
	
	private String getNextLine() {
        String nextLine = strs[blockIndex++];
        return nextLine;
    }
	
	private ArrayList<String> parseLine(String nextLine)  {

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

    private boolean isEscapedQuote(String nextLine, boolean inQuotes, int i) {
        return inQuotes  // we are in quotes, therefore there can be escaped quotes in here.
            && nextLine.length() > (i+1)  // there is indeed another character to check.
            && nextLine.charAt(i+1) == quotechar;
    }
	
}
