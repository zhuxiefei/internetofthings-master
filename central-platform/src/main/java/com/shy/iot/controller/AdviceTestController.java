package com.shy.iot.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/central")
public class AdviceTestController {

    @RequestMapping("/test")
    public void test(String name){
        System.out.println("执行程序"+name);
    }


}
