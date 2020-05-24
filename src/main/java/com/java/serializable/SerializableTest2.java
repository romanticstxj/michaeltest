package com.java.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableTest2 {

	/**
     * @param args
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        House house = new House();
        System.out.println("begin to serialize");
        Animal animal = new Animal("test",house);
        System.out.println(animal);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(animal);
        oos.writeObject(animal);//��дһ�Σ��������Ƿ���һ����
        oos.flush();
        oos.close();
        
        ByteArrayOutputStream out2 = new ByteArrayOutputStream();//��һ�������
        ObjectOutputStream oos2 = new ObjectOutputStream(out2);
        oos2.writeObject(animal);
        oos2.flush();
        oos2.close();

        System.out.println("begin to deserialize");
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(in);
        Animal animal1 = (Animal)ois.readObject();
        Animal animal2 = (Animal)ois.readObject();
        ois.close();
        
        ByteArrayInputStream in2 = new ByteArrayInputStream(out2.toByteArray());
        ObjectInputStream ois2 = new ObjectInputStream(in2);
        Animal animal3 = (Animal)ois2.readObject();
        ois2.close();
        
        System.out.println("out animal1" +animal1);
        System.out.println("out animal2" +animal2);
        System.out.println("out2 animal3" +animal3);
        
        
        System.out.println("(animal==animal1): "+ (animal==animal1));
        System.out.println("(animal1 == animal2): "+ (animal1 == animal2));
        System.out.println("(animal1==animal3): " + (animal1==animal3));
        
    }

}
