import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GCScope {
	GCScope t;
    static int i = 1;
 
    public static void main(String args[]) {
    	List l = new LinkedList();
    	List m = new LinkedList();
    	int i = 0;
        // Enter infinite loop which will add a String to the list: l on each
        // iteration.
        do {
        	if(i%2 == 0){
//        		l.clear();
        		l = m;
        		System.out.println("l clear");
        	}
        	if(i%1 == 0){
        		m = l;
        		System.out.println("l go m");
        	}
        	i++;
            l.add(new String("Hello, World"));
        } while (true);
    }
    
    private static void st(){
    	List list = new ArrayList();
    	list.add("33");
    	list = null;
    }
 
    protected void finalize() {
        System.out.println("Garbage collected from object" + i);
        i++;
    }
}
