package com.mooc.zbs.web.server.servlet;

import com.mooc.zbs.web.server.handler.HandlerManager;
import com.mooc.zbs.web.server.handler.MappingHandler;

import javax.servlet.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class DispatcherServlet implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        //请求过来后用这些handler依次判断能否响应请求，如能处理，就响应结果
        for (MappingHandler mappingHandler: HandlerManager.mappingHandlerList){
            try {
                if (mappingHandler.handle(req,res)){
                    return;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
