
package com.example.QuizApp.data.users;

import com.example.QuizApp.data.Class.Class;
import com.example.QuizApp.data.Class.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        List<User> users = userService.showAll();
        model.addAttribute("users", users);
        return "admin/listUsers";
    }

    @GetMapping("/aIndex/userList/userInfo")
    public String userInfo(@RequestParam Long userID, Model model)
    {
        User user  = userService.showByID(userID);
        model.addAttribute("user", user);
        model.addAttribute("role", getRole(user));
        return "admin/userInfo";
    }



    @GetMapping("aIndex/adminClasses")
    public String showClassesForAdmin(Model model)
    {
        List<Class> classes = classService.showAll();
        model.addAttribute("classes", classes);
        return "admin/listClassesForAdmin";
    }

    @GetMapping("aIndex/adminClasses/addClass")
    public String addClass(Model model)
    {
        List<User> users = userService.showByType(UserType.TEACHER);
        List<Teacher> teachers = new ArrayList<>();
        for(User user: users)
           teachers.add((Teacher) user);
        model.addAttribute("teachers", teachers);
        return "admin/addClass";
    }

    @PostMapping("aIndex/adminClasses/addClass/new")
    public String insertClass(@ModelAttribute Teacher teacher)
    {
        System.out.println("Teacher id "+teacher.getId() +" "+ teacher.getLastName());
        teacher = (Teacher) userService.showByID(teacher.getId());
        classService.insert(new Class(teacher));
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

    private String getRole(User user)
    {
        if(user instanceof Admin)
            return "Admin";
        else if (user instanceof Teacher)
            return "Teacher";
        else
            return "Student";
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
