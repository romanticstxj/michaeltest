package com.java.basic;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

	public static void main(String[] args) {
        Map<String, String> map = new HashMap<>(2);
        map.put("username", "Tom");
        print(map);
    }

    public static void print(Map<String, String> map) {
        try {
            Class<?> mapType = map.getClass();
            Method capacity = mapType.getDeclaredMethod("capacity");
            capacity.setAccessible(true);
            System.out.println("capacity : " + capacity.invoke(map) + "    size : " + map.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
