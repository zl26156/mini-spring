package com.mooc.zbs.beans;

import java.lang.annotation.*;

//标志一个类可以解析为bean
@Documented
@Retention(RetentionPolicy.RUNTIME)
//注解在类上，ElemtType要选type
@Target(ElementType.TYPE)
public @interface Bean {

}
