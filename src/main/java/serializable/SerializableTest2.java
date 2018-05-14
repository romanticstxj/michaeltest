package serializable;

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
        System.out.println("序列化前");
        Animal animal = new Animal("test",house);
        System.out.println(animal);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(animal);
        oos.writeObject(animal);//在写一次，看对象是否是一样，
        oos.flush();
        oos.close();
        
        ByteArrayOutputStream out2 = new ByteArrayOutputStream();//换一个输出流
        ObjectOutputStream oos2 = new ObjectOutputStream(out2);
        oos2.writeObject(animal);
        oos2.flush();
        oos2.close();

        System.out.println("反序列化后");
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(in);
        Animal animal1 = (Animal)ois.readObject();
        Animal animal2 = (Animal)ois.readObject();
        ois.close();
        
        ByteArrayInputStream in2 = new ByteArrayInputStream(out2.toByteArray());
        ObjectInputStream ois2 = new ObjectInputStream(in2);
        Animal animal3 = (Animal)ois2.readObject();
        ois2.close();
        
        System.out.println("out流：" +animal1);
        System.out.println("out流：" +animal2);
        System.out.println("out2流：" +animal3);
        
        
        System.out.println("测试序列化前后的对象 == ："+ (animal==animal1));
        System.out.println("测试序列化后同一流的对象："+ (animal1 == animal2));
        System.out.println("测试序列化后不同流的对象==:" + (animal1==animal3));
        
    }

}
