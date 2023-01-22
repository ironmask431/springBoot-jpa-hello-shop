package com.leesh.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    //스프링 부트 thymeleaf viewName 매핑
    //resources/templates/ +{ViewName}+ .html
    //resources/static  폴더는 정적인 파일 용도 (이미지등)

    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("data","leesh");
        return "hello";
    }
}
