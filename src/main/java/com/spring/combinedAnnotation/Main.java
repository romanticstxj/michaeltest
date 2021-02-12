package com.spring.combinedAnnotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.michael.mail.DemoService1;

public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig.class);
		
//		MyConfiguration myConfiguration = context.getBean("myConfiguration", MyConfiguration.class);
//		System.out.println(myConfiguration);
//		
		DemoService1 demoService1 = context.getBean(DemoService1.class);
		demoService1.outputResult();
		context.close();
	}

}
