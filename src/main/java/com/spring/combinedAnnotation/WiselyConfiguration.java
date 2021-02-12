package com.spring.combinedAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration //组合@Configuration元注解
@ComponentScan //组合@ComponentScan元注解
public @interface WiselyConfiguration {
	@AliasFor(annotation=ComponentScan.class, attribute="value")
	String[] value() default {}; //覆盖value参数

}