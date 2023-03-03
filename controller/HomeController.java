package com.example.KvartiraOnline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home (){
        return "index";
    }

    @GetMapping("/start")
    public String start (){
        return "start";
    }

    @GetMapping("/components")
    public String components (){
        return "components";
    }

    @GetMapping("/charts")
    public String chart (){
        return "charts";
    }

    @GetMapping("/faqs")
    public String faqs (){
        return "faqs";
    }

    @GetMapping("/showcase")
    public String showcase (){
        return "showcase";
    }

    @GetMapping("/license")
    public String license (){
        return "license";
    }

    @GetMapping("/serv")
    public String serv (){
        return "serv";
    }


}
