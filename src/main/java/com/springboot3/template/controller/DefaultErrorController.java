package com.springboot3.template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class DefaultErrorController implements ErrorController {


    @RequestMapping("/api/**")
    public void api() {
        throw new RuntimeException("");
    }

    @RequestMapping("/${template.entry-point.auth}/**")
    public String auth() {
        return "backend/error404";
    }

}
