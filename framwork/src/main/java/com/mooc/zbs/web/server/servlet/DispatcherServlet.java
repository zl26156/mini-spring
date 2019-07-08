package com.mooc.zbs.web.server.servlet;

import com.mooc.zbs.web.server.handler.HandlerManager;
import com.mooc.zbs.web.server.handler.MappingHandler;

import javax.servlet.*;
import java.io.IOException;

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
        for (MappingHandler mappingHandler: HandlerManager.mappingHandlerList){
            if (mappingHandler.handle(req,res)){
                return;
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
