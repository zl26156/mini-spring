package com.mooc.zbs.core;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassScanner {
    public static List<Class<?>> scanClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();//声明一个容器，存储扫描到的类
        String path = packageName.replace(".","/");//包名转换为文件路径，把包名里的.换成 / 就ok了
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();//获取一个类加载器
        Enumeration<URL> resources = classLoader.getResources(path);//调用getResources方法，它的返回值是一个可遍历的URL资源
        //遍历这些资源
        while (resources.hasMoreElements()){
            URL resource = resources.nextElement();//获取资源
            if(resource.getProtocol().contains("jar")){//判断资源类型，如果资源类型为jar包
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();//从资源上获取连接并转换为jar包连接
                String jarFilePath = jarURLConnection.getJarFile().getName();//获取jar包的绝对路径名
                classList.addAll(getClassesFromJar(jarFilePath,path));//添加所有类到list集合 里
            }else {
                //todo
            }
        }
        return classList;
    }

    //根据jar包路径获取jar包下的所有类，path是类的相对路径指定需要 获取 哪些类文件。
    private static List<Class<?>> getClassesFromJar(String jarFilePath,String path) throws IOException, ClassNotFoundException {
        //初始化一个容器，用来存储类
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);//jar包路径转换为jarFile实例
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while(jarEntries.hasMoreElements()){
            JarEntry jarEntry = jarEntries.nextElement();
            String entryName = jarEntry.getName();// entryName长这样 com/mooc/zbs/test/test.class
            if(entryName.startsWith(path)&&entryName.endsWith(".class")){
                String classFullName = entryName.replace("/",".").substring(0,entryName.length()-6);
                classes.add(Class.forName(classFullName));
            }
        }
        return classes;
    }
}
