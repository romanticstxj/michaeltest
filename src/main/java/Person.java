/***********************************************************************************************************************
 * FileName - Person.java
 * 
 * (c) Disney. All rights reserved.
 * 
 * $Author: tengx009 $ 
 * $Revision: #1 $ 
 * $Change: 715510 $ 
 * $Date: May 24, 2019 $
 **********************************************************************************************************************/

public class Person {

    private Integer type;
    private String name;
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public Person(Integer type) {
        this.type = type;
    }
    
    public Person() {
        
    }
    public Person(Integer type, String name) {
        super();
        this.type = type;
        this.name = name;
    }
    @Override
    public String toString() {
        return "Person [type=" + type + ", name=" + name + "]";
    }
}
