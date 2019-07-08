package com.mooc.zbs.web.server.handler;

import java.lang.reflect.Method;

public class MappingHandler {
    private String uri;
    private Method method;
    private Class<?> controller;
    private String args;//调用方法用的参数

    MappingHandler(String uri, Method method, Class<?> cls, String[] args){
        this.uri=uri;
        this.method=method;
        this.controller=cls;
        this.args=args;
    }

}
