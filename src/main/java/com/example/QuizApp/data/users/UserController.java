
package com.example.QuizApp.data.users;

import com.example.QuizApp.data.Class.Class;
import com.example.QuizApp.data.Class.ClassService;
import com.example.QuizApp.data.Class.ClassToStudentRelation;
import com.example.QuizApp.data.result.QuizResult;
import com.example.QuizApp.data.result.QuizResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ClassService classService;
    private final UserRepository userRepository;

    private QuizResultService quizResultService;

    @Autowired
    public UserController(UserService userService, ClassService classService,
                          UserRepository userRepository, QuizResultService quizResultService)
    {
        this.userService = userService;
        this.classService = classService;
        this.userRepository = userRepository;
        this.quizResultService = quizResultService;
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
        {
            List<QuizResult> quizResultList = quizResultService.getResultsByStudent((Student) currentUser);
            Float mean = 0.0F;
            int nuberOfQuizes = 0;
            int numberOfScoredQuizes = 0;
            for(QuizResult result: quizResultList)
            {
                if(result.getAttended())
                {
                    if (result.getCountsToAvg()) {
                        mean += result.getMark();
                        numberOfScoredQuizes++;
                    }
                    nuberOfQuizes++;
                }
            }
            if(mean>0)
                mean = mean/numberOfScoredQuizes;
            model.addAttribute("mean", mean);
            model.addAttribute("numberOfQuizes",nuberOfQuizes);
            return "misc/studentSelf";
        }

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
        model.addAttribute("passwordEmptyErr", false);
        return "misc/changePasswordSelf";
    }

    @PostMapping("/self/newPassword")
    public String newPassword(@RequestParam @NotEmpty String password, Model model)
    {
        if(password.isEmpty()){
            model.addAttribute("passwordEmptyErr", true);
            return "misc/changePasswordSelf";
        }
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
        Student student = (Student) userService.showByID(ID);
        List<QuizResult> quizResultList = quizResultService.getResultsByStudent(student);
        Float mean = 0.0F;
        int nuberOfQuizes = 0;
        for(QuizResult result: quizResultList)
        {
            if(result.getAttended())
            {
                mean += result.getMark();
                nuberOfQuizes++;
            }
        }
        if(mean>0)
            mean = mean/quizResultList.size();
        model.addAttribute("mean", mean);
        model.addAttribute("numberOfQuizes",nuberOfQuizes);
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


    @PostMapping("/aIndex/userList/delete")
    public String deleteUser(@RequestParam Long userID, Model model){
        userService.deleteUserById(userID);

        return "redirect:/user/aIndex/userList";
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
        model.addAttribute("loginTakenErr", false);
        return "admin/addStudent";
    }

    @PostMapping("/aIndex/addUserMenu/addStudent/new")
    public String insertStudent(@Valid @ModelAttribute("newStudent") Student student,
                                BindingResult result, Model model) {
        if (result.hasErrors()){
            return "admin/addStudent";
        }
        try {
            userService.insert(student);
        } catch (DataIntegrityViolationException e){
            model.addAttribute("loginTakenErr", true);
            return "admin/addStudent";
        }
        return "redirect:/user/aIndex/addUserMenu";
    }

    @PostMapping("/aIndex/addUserMenu/addTeacher/new")
    public String insertTeacher(@Valid @ModelAttribute("newTeacher") Teacher teacher,
                                BindingResult result, Model model) {
        if (result.hasErrors()){
            return "admin/addTeacher";
        }
        try {
            userService.insert(teacher);
        } catch (DataIntegrityViolationException e){
        model.addAttribute("loginTakenErr", true);
        return "admin/addTeacher";
    }
        return "redirect:/user/aIndex/addUserMenu";
    }

    @PostMapping("/aIndex/addUserMenu/addAdmin/new")
    public String insertAdmin(@Valid @ModelAttribute("newAdmin") Admin admin,
                              BindingResult result, Model model) {
        if (result.hasErrors()){
            return "admin/addAdmin";
        }
        try {
            userService.insert(admin);
        } catch (DataIntegrityViolationException e){
            model.addAttribute("loginTakenErr", true);
            return "admin/addAdmin";
        }
        return "redirect:/user/aIndex/addUserMenu";
    }

    @GetMapping("/aIndex/addUserMenu/addTeacher")
    public String addTeacher(Model model)
    {
        model.addAttribute("newTeacher", new Teacher());
        model.addAttribute("loginTakenErr", false);
        return "admin/addTeacher";
    }

    @GetMapping("/aIndex/addUserMenu/addAdmin")
    public String addAdmin(Model model)
    {
        model.addAttribute("newAdmin", new Admin());
        model.addAttribute("loginTakenErr", false);
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
        model.addAttribute("classErr", false);
        model.addAttribute("existsErr", false);
        return "student/joinClass";
    }

    @PostMapping("/joinClass/join")
    public String joinClass(@RequestParam String code, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DBUserDetails details = (DBUserDetails) auth.getPrincipal();
        Student currentStudent = (Student) details.getUser();
        try {
        Class myClass = classService.getClassById(Long.parseLong(code));
        ClassToStudentRelation newRel = new ClassToStudentRelation();
        newRel.setMyClass(myClass);
        newRel.setStudent(currentStudent);
        classService.insertClassRel(newRel);
        return"redirect:/user/self";
        } catch (DataIntegrityViolationException | NumberFormatException e){
            model.addAttribute("code", "");
            model.addAttribute("classErr", true);
            model.addAttribute("existsErr", false);
            return "student/joinClass";
        } catch (IllegalStateException e){
            model.addAttribute("code", "");
            model.addAttribute("classErr", false);
            model.addAttribute("existsErr", true);
            return "student/joinClass";
        }
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
