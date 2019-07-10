package com.mooc.zbs.web.server.handler;

import com.mooc.zbs.web.server.mvc.Controller;
import com.mooc.zbs.web.server.mvc.RequestMapping;
import com.mooc.zbs.web.server.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class HandlerManager {
    //handler静态管理器
    public static List<MappingHandler> mappingHandlerList = new ArrayList<>();

    public static void resolveMappingHandler(List<Class<?>> classList){
        for (Class<?> cls: classList){
            //遍历传入的classList，并且判断当前类是否有controller注解
            if(cls.isAnnotationPresent(Controller.class)){
                //解析这个controller类
                parseHandlerFromController(cls);
            }
        }
    }

    public static void parseHandlerFromController(Class<?> cls){
        //通过反射获取这个类的所有方法
        Method[] methods = cls.getDeclaredMethods();
        for (Method method:methods){//遍历methods中的所有method方法
            //找出并处理所有被RequestMapping注解的方法，如果不存在就不做处理
            if(!method.isAnnotationPresent(RequestMapping.class)){
                continue;//continue的作用是不执行后面的代码，直接进行下一次循环
            }
            //获取method里的uri
            String uri= method.getDeclaredAnnotation(RequestMapping.class).value();
            List<String> paramNameList = new ArrayList<>();
            //遍历method中的所有参数
            for (Parameter parameter : method.getParameters()){
                //找出所有被RequestParam注解的参数
                if (parameter.isAnnotationPresent(RequestParam.class)){
                    //从RequestParam注解获取参数名
                    paramNameList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                }
            }
            //参数名容器转化为数组形式，参数数组
            String[] params = paramNameList.toArray(new String[paramNameList.size()]);
            //实例化MappingHandler
            MappingHandler mappingHandler = new MappingHandler(uri,method,cls,params);
            //将实例化后的mappingHandler对象添加到mappingHandlerList容器中去
            HandlerManager.mappingHandlerList.add(mappingHandler);
        }
    }
}
