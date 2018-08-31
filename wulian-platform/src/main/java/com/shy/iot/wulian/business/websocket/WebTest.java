package com.shy.iot.wulian.business.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebTest {
    @RequestMapping(value = "/test")
    public String test() {
        return "index";
    }
}