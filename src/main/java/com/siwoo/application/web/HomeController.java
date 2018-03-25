package com.siwoo.application.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/home","/index","/"})
public class HomeController {

    @GetMapping
    public String home() {
        return "home";
    }
}
