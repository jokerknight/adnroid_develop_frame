package com.hr.aframe.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 自定义注解
 */
@Target(ElementType.FIELD)//表示用在字段上
@Retention(RetentionPolicy.RUNTIME)//表示在生命周期是运行时 
public @interface  ViewInject {
	 int value() default 0; 
}
