package com.ggwp.memberservice.testview;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testviewController {
    @GetMapping("/member/signupform")
    public String signupForm(){
        return "/signup";
    }


    @GetMapping("/member/loginform")
    public String loginform(){
        return "/login";
    }
    //테스트
    
}
