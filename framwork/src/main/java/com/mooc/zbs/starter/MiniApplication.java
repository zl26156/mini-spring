package com.mooc.zbs.starter;

import com.mooc.zbs.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;


public class MiniApplication {
    public static void run(Class<?> cls,String[] args){
        System.out.println("hello miniSpring");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
