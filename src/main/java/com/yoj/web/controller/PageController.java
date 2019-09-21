package com.yoj.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {
//    private final String PREFIX = "";

    /**
     * problem页面映射
     * @param path
     * @return
     */
    @GetMapping("/problem/{path}")
    public String level2(@PathVariable("path")String path) {
        return "problem/"+path;
    }

    /**
     * user页面映射
     * @param path
     * @return
     */
    @GetMapping("/user/{path}")
    public String level3(@PathVariable("path")String path) {
        return "user/"+path;
    }

    /**
     * user页面映射
     * @param path
     * @return
     */
    @GetMapping("/solution/{path}")
    public String solution(@PathVariable("path")String path) {
        return "solution/"+path;
    }
}
