package com.mooc.zbs.service;

import com.mooc.zbs.beans.Bean;

@Bean
public class SalaryService {
    public Integer calSalary (Integer experience){
        return experience*5000;
    }
}
