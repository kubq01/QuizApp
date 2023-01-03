package com.example.QuizApp.data.users;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserControllerT {

    @GetMapping("/index")
    public String getIndex(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DBUserDetails details = (DBUserDetails) auth.getPrincipal();
        User currentUser = (User) details.getUser();
        System.out.println(currentUser.getClass().getSimpleName());
        switch (currentUser.getClass().getSimpleName()){
            case "Admin":
                return "admin/adminIndex";
            case "Student":
                return "student/studentIndex";
            case "Teacher":
                return "teacher/teacherIndex";
            default:
                return "login";
        }
    }
}
