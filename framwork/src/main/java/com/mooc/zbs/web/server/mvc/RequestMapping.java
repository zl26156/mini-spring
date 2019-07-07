package com.mooc.zbs.web.server.mvc;

import java.lang.annotation.*;

//添加3个原注解
@Documented
@Retention(RetentionPolicy.RUNTIME) //保留到运行期
@Target(ElementType.METHOD)//作用目标是Controller里的方法，ElementType选METHOD
public @interface RequestMapping {
    String value();//保存需要映射的url
}
