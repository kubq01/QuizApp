
package com.example.QuizApp.data.users;

import com.example.QuizApp.data.Class.Class;
import com.example.QuizApp.data.Class.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ClassService classService;

    @Autowired
    public UserController(UserService userService, ClassService classService)
    {
        this.userService = userService;
        this.classService = classService;
    }

    @GetMapping("/aIndex")
    public String showAdminIndex( Model model)
    {
        return "admin/adminIndex";
    }

    @GetMapping("/myAccount")
    public String showAccount(Model model)
    {
        return "misc/userSelf";
    }

    @GetMapping("/myAccount/changePassword")
    public String changePassword(Model model)
    {
        //TODO:add post mapping for changing password
        return "misc/changePasswordSelf";
    }

    @GetMapping("/aIndex/userList")
    public String listUser(Model model)
    {
        return "admin/listUsers";
    }

    @GetMapping("/aIndex/userList/userInfo")
    public String userInfo(Model model)
    {
        return "admin/userInfo";
    }

    @GetMapping("aIndex/adminClasses")
    public String showClassesForAdmin(Model model)
    {
        return "admin/listClassesForAdmin";
    }

    @GetMapping("aIndex/adminClasses/addClass")
    public String addClass(Model model)
    {
        return "admin/addClass";
    }

    @PostMapping("aIndex/adminClasses/addClass/new")
    public String insertClass(@ModelAttribute Class newClass)
    {
        classService.insert(newClass);
        return "redirect:/user/aIndex/adminClasses";
    }

    @GetMapping("/aIndex/addUserMenu")
    public String addUserMenu(Model model)
    {
        return "admin/addNewUser";
    }

    @GetMapping("/aIndex/addUserMenu/addStudent")
    public String addUser(Model model)
    {
        return "admin/addStudent";
    }

    @GetMapping("/aIndex/addUserMenu/addTeacher")
    public String addTeacher(Model model)
    {
        return "admin/addTeacher";
    }

    @GetMapping("/aIndex/addUserMenu/addAdmin")
    public String addAdmin(Model model)
    {
        return "admin/addAdmin";
    }

}

/*
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/current")
    public User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DBUserDetails details = (DBUserDetails) auth.getPrincipal();
        User currentUser = (User) details.getUser();
        currentUser.setPassword("*protected*");
        return currentUser;
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User newUser){
        userService.insert(newUser);
        return newUser;
    }

    @GetMapping("/list")
    public List<User> getUsers(){
        return userService.showAll();
    }

    @GetMapping("/list/students")
    public List<User> getStudents(){
        return userService.showByType(UserType.STUDENT);
    }

    @GetMapping("/list/teachers")
    public List<User> getTeachers(){
        return userService.showByType(UserType.TEACHER);
    }
}
*/
