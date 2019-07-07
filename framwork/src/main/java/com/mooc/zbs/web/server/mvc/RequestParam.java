package com.mooc.zbs.web.server.mvc;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME) //保留到运行期
@Target(ElementType.PARAMETER)//作用目标是Controller方法的参数上，ElementType选PARAMETER
public @interface RequestParam {
    String value();//接收的key
}
