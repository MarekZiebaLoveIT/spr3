package com.example.spr3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ViewContorller {

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("about-us")
    public String aboutUs() {
        return "aboutUs";
    }

}
