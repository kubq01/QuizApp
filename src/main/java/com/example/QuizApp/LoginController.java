package com.example.QuizApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/home")
    public String home(){
        return"redirect:/user/index";
    }

    @GetMapping("/loginError")
    public String wrongLogin()
    {
        return "loginError";
    }

}
