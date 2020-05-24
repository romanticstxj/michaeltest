package com.java.serializable;


import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author chenfei
 * 
 * ���ڲ������л�ʱ��deep copy
 *
 */
public class House implements Serializable {
    private static final long serialVersionUID = -6091530420906090649L;
    
    private Date date = new Date(); //��¼��ǰ��ʱ��
    
    public String toString() {
        return "House:" + super.toString() + ".Create Time is:" + date;
    }

}