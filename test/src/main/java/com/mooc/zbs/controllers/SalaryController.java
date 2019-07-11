package com.mooc.zbs.controllers;

import com.mooc.zbs.beans.AutoWired;
import com.mooc.zbs.service.SalaryService;
import com.mooc.zbs.web.server.mvc.Controller;
import com.mooc.zbs.web.server.mvc.RequestMapping;
import com.mooc.zbs.web.server.mvc.RequestParam;

@Controller
public class SalaryController {
    @AutoWired
    private SalaryService salaryService;
    //设置请求url
    @RequestMapping("/get_salary.json")
    //这里给参数直接传值，用方法参数注解
    public Integer getSalary(@RequestParam("name") String name,@RequestParam("experience") String experience){
        return salaryService.calSalary(Integer.parseInt(experience));
    }
}
