
package com.example.QuizApp.data.users;

import com.example.QuizApp.data.Class.Class;
import com.example.QuizApp.data.Class.ClassService;
import com.example.QuizApp.data.Class.ClassToStudentRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        model.addAttribute("role", getRole(currentUser));
        if(currentUser instanceof Student)
            return "misc/studentSelf";

        return "misc/userSelf";
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

    @GetMapping("/self/changePassword")
    public String changePassword(Model model)
    {
        return "misc/changePasswordSelf";
    }

    @PostMapping("/self/newPassword")
    public String newPassword(@RequestParam String password)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DBUserDetails details = (DBUserDetails) auth.getPrincipal();
        User currentUser = (User) details.getUser();
        userService.updatePassword(currentUser, password);
        return "redirect:/user/self";
    }

    @GetMapping("/listClasses")
    public String listClasses(Model model)
    {
        List<Class> classes = classService.showAll();
        model.addAttribute("classes", classes);
        return "teacher/listClasses";
    }

    @GetMapping("/listStudents")
    public String listStudents(@RequestParam Long classID, Model model)
    {
        model.addAttribute("students", classService.getStudentsByClass(classID));
        return "teacher/listStudents";
    }

    @GetMapping("/studentInfoTeacher")
    public String studentInfoTeacher(@RequestParam Long ID, Model model)
    {
        model.addAttribute("student", userService.showByID(ID));
        return "teacher/studentInfoForTeacher";
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
        model.addAttribute("newStudent", new Student());
        return "admin/addStudent";
    }

    @PostMapping("/aIndex/addUserMenu/addStudent/new")
    public String insertStudent(@ModelAttribute Student student)
    {
        userService.insert(student);
        return "redirect:/user/aIndex/addUserMenu";
    }

    @PostMapping("/aIndex/addUserMenu/addTeacher/new")
    public String insertTeacher(@ModelAttribute Teacher teacher)
    {
        userService.insert(teacher);
        return "redirect:/user/aIndex/addUserMenu";
    }

    @PostMapping("/aIndex/addUserMenu/addAdmin/new")
    public String insertAdmin(@ModelAttribute Admin admin)
    {
        userService.insert(admin);
        return "redirect:/user/aIndex/addUserMenu";
    }

    @GetMapping("/aIndex/addUserMenu/addTeacher")
    public String addTeacher(Model model)
    {
        model.addAttribute("newTeacher", new Teacher());
        return "admin/addTeacher";
    }

    @GetMapping("/aIndex/addUserMenu/addAdmin")
    public String addAdmin(Model model)
    {
        model.addAttribute("newAdmin", new Admin());
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

    @GetMapping("/joinClass")
    public String showJoinClass(Model model){
        model.addAttribute("code", "");
        return "student/joinClass";
    }

    @PostMapping("/joinClass/join")
    public String joinClass(@ModelAttribute String code, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DBUserDetails details = (DBUserDetails) auth.getPrincipal();
        Student currentStudent = (Student) details.getUser();
        Class myClass = classService.getClassById(Long.parseLong(code));
        ClassToStudentRelation newRel = new ClassToStudentRelation();
        newRel.setMyClass(myClass);
        newRel.setStudent(currentStudent);
        classService.insertClassRel(newRel);
        return"redirect:/user/myAccount";
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
