package com.example.QuizApp.data.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserControllerT {

    private final UserService userService;

    @Autowired
    public UserControllerT(UserService userService) {
        this.userService = userService;
    }

    /*
    @GetMapping("/index")
    public String getIndex(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DBUserDetails details = (DBUserDetails) auth.getPrincipal();
        User currentUser = (User) details.getUser();
        model.addAttribute("currUser", currentUser);
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

    @GetMapping("/self")
    public String selfInfo(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DBUserDetails details = (DBUserDetails) auth.getPrincipal();
        User currentUser = (User) details.getUser();
        model.addAttribute("currUser", currentUser);
        return "misc/userSelf";
    }

     */

}
