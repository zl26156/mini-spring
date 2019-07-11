package com.mooc.zbs.beans;

import java.lang.annotation.*;
//这3个是依赖注解
@Documented
@Retention(RetentionPolicy.RUNTIME)
//目标是类属性
@Target(ElementType.FIELD)
//这个注解的bean会添加相应的依赖
public @interface AutoWired {
}
