package com.michael.mail;

import org.springframework.stereotype.Service;

@Service
public class DemoService1 {
	public void outputResult(){
	     System.out.println("从组合注解配置照样获得的bean1");
	   }
}
