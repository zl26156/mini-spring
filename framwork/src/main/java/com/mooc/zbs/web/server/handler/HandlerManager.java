package com.mooc.zbs.web.server.handler;

import com.mooc.zbs.web.server.mvc.Controller;
import com.mooc.zbs.web.server.mvc.RequestMapping;
import com.mooc.zbs.web.server.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class HandlerManager {
    public static List<MappingHandler> mappingHandlerList = new ArrayList<>();

    public static void resolveMappingHandler(List<Class<?>> classList){
        for (Class<?> cls: classList){
            if(cls.isAnnotationPresent(Controller.class)){
                parseHandlerFromController(cls);
            }
        }
    }

    public static void parseHandlerFromController(Class<?> cls){
        Method[] methods = cls.getDeclaredMethods();
        for (Method method:methods){
            if(method.isAnnotationPresent(RequestMapping.class)){
                continue;
            }
            String uri= method.getDeclaredAnnotation(RequestMapping.class).value();
            List<String> parameterList = new ArrayList<>();
            for (Parameter parameter : method.getParameters()){
                if (parameter.isAnnotationPresent(RequestParam.class)){
                    parameterList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                }
            }
            String[] params = parameterList.toArray(new String[parameterList.size()]);
            MappingHandler mappingHandler = new MappingHandler(uri,method,cls,params);
            HandlerManager.mappingHandlerList.add(mappingHandler);
        }
    }
}
