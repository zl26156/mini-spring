package com.mooc.zbs.web.server;


import com.mooc.zbs.web.server.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

public class TomcatServer {
    private Tomcat tomcat;
    private String[] args;

    public TomcatServer(String[] args){
        this.args = args;
    }
    //设置启动server
    public void startServer() throws LifecycleException {
        tomcat =new Tomcat();
        tomcat.setPort(6699);
        tomcat.start();

        //创建tomcat和servlet的联系
        Context context = new StandardContext();//创建容器，用tomcat自带的标准context容器就行
        context.setPath("");//设置路径
        context.addLifecycleListener( new Tomcat.FixContextListener());//设置生命周期监听器，用默认的监听器
        DispatcherServlet servlet = new DispatcherServlet();
        //将TestServlet注入到context容器内，这里用Tomcat自带的静态方法addServlet
        Tomcat.addServlet(context,"dispatcherServlet",servlet).setAsyncSupported(true);
        //添加servlet道url的映射，以便通过url访问这个servlet
        context.addServletMappingDecoded("/","dispatcherServlet");
        //把context容器注册到host容器中
        tomcat.getHost().addChild(context);

        //设置等待线程
        Thread awaitThread = new Thread( "tomcat_await_thread"){
            @Override
            public void run(){
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        //设置为非守护线程
        awaitThread.setDaemon(false);
        //调用方法
        awaitThread.start();
    }
}
