package serializable;

import java.io.Serializable;

public class Animal implements Serializable {
    private static final long serialVersionUID = -213221189192962074L;
    
    private String name;
    
    private House house;
    
    public Animal(String name , House house) {
        this.name = name;
        this.house = house;
        System.out.println("�����˹�����");
    }
    
    public String toString() {
        return  name + "[" +super.toString() + "']" + house;
    }

}