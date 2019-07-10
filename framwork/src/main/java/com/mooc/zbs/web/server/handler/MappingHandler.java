package com.mooc.zbs.web.server.handler;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MappingHandler {
    private String uri;
    private Method method;
    private Class<?> controller;
    private String[] args;//调用方法用的参数

    //设置构造函数，将传入的controller和uri等通过注入的方式给这个MappingHandler对象
    MappingHandler(String uri, Method method, Class<?> cls, String[] args){
        this.uri=uri;
        this.method=method;
        this.controller=cls;
        this.args=args;
    }

    public boolean handle (ServletRequest req, ServletResponse res) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        //将请求转换为HttpServletRequest，谈话获取uri
        String requstUri = ((HttpServletRequest)req).getRequestURI();
        //判断handle中存储的uri和请求uri是否相等，如果相等，说明当前handler可以处理当前请求
        if(!requstUri.equals(uri)){
            return false;
        }
        //如果相等调用method方法
        //准备一下参数
        Object[] parameters = new Object[args.length];
        //遍历参数，依次从handler获取到这些参数。
        for(int i=0;i<args.length;i++){
            parameters[i] = req.getParameter(args[i]);
        }

        //实例化controller
        Object ctl = controller.newInstance();
        //用Object来存储结果
        Object response = method.invoke(ctl,parameters);
        //方法返回的结果放回到ServletResponse 中去
        res.getWriter().println(response.toString());
        //处理成功后，返回true
        return true;
    }

}
