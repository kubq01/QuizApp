package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.ExerciseRepository;
import com.example.QuizApp.data.exercises.ExerciseService;
import com.example.QuizApp.data.users.DBUserDetails;
import com.example.QuizApp.data.users.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizControllerT {

    private final QuizService quizService;
    private final ExerciseService exerciseService;

    private List<Exercise> tempExercises;

    private Long tempId;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public QuizControllerT(QuizService service, ExerciseService exerciseService,
                           ExerciseRepository exerciseRepository){
        this.quizService = service;
        this.exerciseService = exerciseService;
        tempExercises = new ArrayList<>();
        this.exerciseRepository = exerciseRepository;
    }

    @GetMapping("/listByT")
    public String getSafeByID(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DBUserDetails details = (DBUserDetails) auth.getPrincipal();
        Teacher currentTeacher = (Teacher) details.getUser();
        model.addAttribute("quizList", quizService.showAllByTeacher(currentTeacher));
        try {
            return "teacher/listQuizes";
        }
        catch (IllegalArgumentException exception){
            exception.printStackTrace();
            return null;   //TODO przerobić na zwrócenie 400 Bad Request
        }
    }

    @GetMapping("/addTeacherQuiz")
    public String showAddTeacherQuiz(
            @RequestParam(name="reset", required = false, defaultValue = "false")
                                     Boolean reset, Model model){
        if (reset){
            tempExercises.clear();
            tempId = 0L;
        }
        model.addAttribute("newQuiz", new TeacherQuiz());
        model.addAttribute("exercises", tempExercises);
        return "teacher/addQuiz";
    }

    @PostMapping("/addTeacherQuiz/new")
    public String addTeacherQuiz(@ModelAttribute TeacherQuiz quiz ,Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DBUserDetails details = (DBUserDetails) auth.getPrincipal();
        Teacher currentTeacher = (Teacher) details.getUser();
        quiz.setTeacher(currentTeacher);
        Quiz newQuiz = quizService.insert(quiz);
        for (Exercise exercise:tempExercises) {
            exercise.setQuiz(newQuiz);
            exerciseService.insert(exercise);
        }
        return "redirect:/quiz/listByT";
    }



    @GetMapping("/addABCD")
    public String showAddABCD(Model model){
        model.addAttribute("newABCD", new ABCDExercise());
        return "teacher/addABCD";
    }

    @PostMapping("/addABCD/new")
    public String addABCD(@ModelAttribute ABCDExercise exercise ,Model model){
        exercise.setId(tempId++);
        exercise.setPoints(1);
        tempExercises.add(exercise);
        return "redirect:/quiz/addTeacherQuiz";
    }
}
