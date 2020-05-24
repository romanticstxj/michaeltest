import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

/***********************************************************************************************************************
 * FileName - Jdk8Test.java
 * 
 * (c) Disney. All rights reserved.
 * 
 * $Author: tengx009 $ 
 * $Revision: #1 $ 
 * $Change: 715510 $ 
 * $Date: May 24, 2019 $
 **********************************************************************************************************************/

public class Jdk8Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Integer[] integer2 = new Integer[]{0};
        
        Integer ii = Lists.newArrayList(1, 2, 3, 4, 5)
                .stream()
                .collect(() -> new Integer(0), (a, x) -> a += x, (a1, a2) -> a1 += a2);
        
            System.out.println(ii);
            
            Map<Integer,List<Person>> map = Lists.<Person>newArrayList(new Person(1,"michael")
                    , new Person(1,"michael1"), new Person(2,"Jason"), new Person(3,"Hello"))
                    .stream().collect(() -> new HashMap<Integer,List<Person>>(),
                (h, x) -> {
                  List<Person> value = h.getOrDefault(x.getType(), Lists.newArrayList());
                  value.add(x);
                  System.out.println("h put " + x);
                  h.put(x.getType(), value);
                },
                HashMap::putAll
            );
            System.out.println(map);
    }

}
