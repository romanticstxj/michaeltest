import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

/***********************************************************************************************************************
 * FileName - GenericTest.java
 * 
 * (c) Disney. All rights reserved.
 * 
 * $Author: tengx009 $ 
 * $Revision: #1 $ 
 * $Change: 715510 $ 
 * $Date: Aug 20, 2019 $
 **********************************************************************************************************************/

public class GenericTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        List<String> testList = Lists.newArrayList();
        test(testList);
        
        List<? extends Person> list2 = new ArrayList<>();
        list2.add(new Father());
        
        List<Father> list3 = new ArrayList<>();
        test1(list3);
    }
    
    public static void test(List<?> list) {
        
    }
    
    public static void test1(List<? extends Person> list) {
    	list.add(new Father());
    }

}
