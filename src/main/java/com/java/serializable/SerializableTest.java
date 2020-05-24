package com.java.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Object Animal before serialization is not the same as
 * object Animal1 after deserialization
 */
public class SerializableTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		House house = new House();
        System.out.println("begin to serialize");
        Animal animal = new Animal("test",house);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(animal);
        oos.flush();
        oos.close();
        System.out.println(animal.toString());

        System.out.println("begin to deserialize");
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(in);
        Animal animal1 = (Animal)ois.readObject();
        ois.close();    
        System.out.println(animal1.toString());
	}

}
