package com.mooc.zbs.web.server.mvc;

import java.lang.annotation.*;
//添加3个原注解
@Documented
@Retention(RetentionPolicy.RUNTIME) //保留到运行期
@Target(ElementType.TYPE)//作用目标是类，ElementType选type
public @interface Controller {
}
