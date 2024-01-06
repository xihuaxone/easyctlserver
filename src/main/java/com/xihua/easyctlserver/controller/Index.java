package com.xihua.easyctlserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class Index {

    @GetMapping(value = "")
    public String hello(@RequestParam(value = "name", required = false) String name) {
        return "hello " + (name == null ? "you" : name);
    }
}