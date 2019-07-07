package com.mooc.zbs.starter;

import com.mooc.zbs.core.ClassScanner;
import com.mooc.zbs.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;


public class MiniApplication {
    public static void run(Class<?> cls,String[] args){
        System.out.println("hello miniSpring");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
            List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
            classList.forEach(it-> System.out.println(it.getName()));
        } catch (LifecycleException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
