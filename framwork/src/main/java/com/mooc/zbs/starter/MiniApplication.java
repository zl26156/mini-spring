package com.mooc.zbs.starter;

import com.mooc.zbs.core.ClassScanner;
import com.mooc.zbs.web.server.TomcatServer;
import com.mooc.zbs.web.server.handler.HandlerManager;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

//框架入口类
public class MiniApplication {
    public static void run(Class<?> cls,String[] args){
        System.out.println("hello miniSpring，进入框架入口类，开始启动tomcat");

        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            //启动tomcat
            tomcatServer.startServer();
            //获取所有包下的类
            List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
            //初始化所有MappingHandler，resolv方法里会筛选出controller类
            HandlerManager.resolveMappingHandler(classList);
            //输出获取类的名称
            classList.forEach(it-> System.out.println(it.getName()));
        } catch (LifecycleException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
