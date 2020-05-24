import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/***********************************************************************************************************************
 * FileName - SerializationTest.java
 * 
 * (c) Disney. All rights reserved.
 * 
 * $Author: tengx009 $ $Revision: #1 $ $Change: 715510 $ $Date: May 24, 2019 $
 **********************************************************************************************************************/

public class Test extends Father implements Serializable {

    private static final long serialVersionUID = 1L;

    public static int staticVar = 5;

    public int i = 50;

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
    	List<BigDecimal> list = new ArrayList<>();
		list.add(new BigDecimal(1.2222));
		list.add(new BigDecimal(2.3321));
		BigDecimal result = list.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println(result);
//        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("result.obj"));
//        ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream("result2.obj"));
//        Test test = new Test();
//        // 试图将对象两次写入文件
//        out.writeObject(test);
//        out.close();
//        System.out.println(new File("result.obj").length());
//        out2.writeObject(test);
//        out2.close();
//        System.out.println(new File("result2.obj").length());
//
//        ObjectInputStream oin = new ObjectInputStream(new FileInputStream("result.obj"));
//        ObjectInputStream oin2 = new ObjectInputStream(new FileInputStream("result2.obj"));
//        // 从文件依次读出两个文件
//        Test t1 = (Test) oin.readObject();
//        Test t2 = (Test) oin2.readObject();
//        oin.close();
//        oin2.close();
//
//        // 判断两个引用是否指向同一个对象
//        System.out.println(t1 == t2);
        
//        test2();
    }
    
    public static void test2() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("result.obj"));
        Test test = new Test();
        test.i = 1;
        out.writeObject(test);
        out.flush();
        test.i = 2;
        out.writeObject(test);
        out.close();
        ObjectInputStream oin = new ObjectInputStream(new FileInputStream(
                            "result.obj"));
        Test t1 = (Test) oin.readObject();
        Test t2 = (Test) oin.readObject();
        System.out.println(t1.i);
        System.out.println(t2.i);
        oin.close();
    }

}
