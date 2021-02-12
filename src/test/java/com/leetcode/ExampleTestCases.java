package com.leetcode;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class ExampleTestCases {
	@Test
    public void itShouldHandleSimpleInputs() {
        try {
            ArrayList<ArrayList<String>> expected = Util.toArrayList(
                new String[][] {{"1","2","3",},{"4","5","6"}}
            );
            ArrayList<ArrayList<String>> actual = CSVParser.parse(
                "1,2,3\n4,5,6", ',', '\"'
            );
            assertEquals(expected, actual);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void itShouldHandleQuotedFields() {
        try {
            ArrayList<ArrayList<String>> expected = Util.toArrayList(
                new String[][] {{"1","two was here","3"},{"4","5","6"}}
            );
            ArrayList<ArrayList<String>> actual = CSVParser.parse(
                "1,\"two was here\",3\n4,5,6", ',', '\"'
            );
            assertEquals(expected, actual);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void itShouldHandleAlternateSeparators() {
        try {
            ArrayList<ArrayList<String>> expected = Util.toArrayList(
                new String[][] {{"1","2","3"},{"4","5","6"}}
            );
            ArrayList<ArrayList<String>> actual = CSVParser.parse(
                "1\t2\t3\n4\t5\t6", '\t', '\"'
            );
            assertEquals(expected, actual);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}

class Util {
    static ArrayList<ArrayList<String>> toArrayList(String[][] data) {
        ArrayList<ArrayList<String>> al = new ArrayList<>();
        
        for (String[] r : data) {
            ArrayList<String> row = new ArrayList<String>();
            al.add(row);
            
            for (String s : r) { row.add(s); }
        }
        
        return al;
    }
}
