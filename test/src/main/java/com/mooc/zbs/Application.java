package com.mooc.zbs;

import com.mooc.zbs.starter.MiniApplication;

public class Application {
    public static void main(String[] args) {
        System.out.println("hello world，项目调用成功，开始调用框架");
        MiniApplication.run(Application.class,args);
    }
}
