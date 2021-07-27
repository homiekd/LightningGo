package com.kdwu.lightninggo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String helloProject() {
        return "Hello, Lightning GO Project!!";
    }
}
