package serializable;


import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author chenfei
 * 
 * 用于测试序列化时的deep copy
 *
 */
public class House implements Serializable {
    private static final long serialVersionUID = -6091530420906090649L;
    
    private Date date = new Date(); //记录当前的时间
    
    public String toString() {
        return "House:" + super.toString() + ".Create Time is:" + date;
    }

}