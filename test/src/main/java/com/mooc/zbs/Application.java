package com.mooc.zbs;

import com.mooc.zbs.starter.MiniApplication;

public class Application {
    public static void main(String[] args) {
        System.out.println("hello world");
        MiniApplication.run(Application.class,args);
    }
}
