package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.Class.Class;
import com.example.QuizApp.data.Class.ClassService;
import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.ExerciseService;
import com.example.QuizApp.data.result.QuizResult;
import com.example.QuizApp.data.result.QuizResultService;
import com.example.QuizApp.data.users.DBUserDetails;
import com.example.QuizApp.data.users.Student;
import com.example.QuizApp.data.users.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizControllerT {

    private final QuizService quizService;
    private final ExerciseService exerciseService;
    private final QuizResultService resultService;

    private final ClassService classService;

    private List<Exercise> tempExercises;
    private List<ABCDExercise> tempABCDExercises = new ArrayList<>();

    private Long tempId;

    private TeacherQuiz tempQuiz;
    private int exerciseCounter = -1;

    @Autowired
    public QuizControllerT(QuizService service, ExerciseService exerciseService, QuizResultService resultService, ClassService classService){
        this.quizService = service;
        this.exerciseService = exerciseService;
        this.resultService = resultService;
        this.classService = classService;
        tempExercises = new ArrayList<>();
    }

    @GetMapping("/listByT")
    public String listQuizesByCurrentTeacher(Model model)
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
    public String addTeacherQuiz(@ModelAttribute TeacherQuiz quiz, @RequestParam String code, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DBUserDetails details = (DBUserDetails) auth.getPrincipal();
        Teacher currentTeacher = (Teacher) details.getUser();
        Class studentsClass = classService.getClassById(Long.parseLong(code));
        quiz.setTeacher(currentTeacher);
        quiz.setStudentsClass(studentsClass);
        TeacherQuiz newQuiz = (TeacherQuiz) quizService.insert(quiz);
        for (Exercise exercise:tempExercises) {
            exercise.setQuiz(newQuiz);
            exerciseService.insert(exercise);
        }
        List<Student> students = classService.getStudentsByClass(studentsClass.getId());
        for(Student student: students){
            QuizResult newResult = new QuizResult(null, null, false,
                    0, 0, newQuiz, student);
            resultService.insert(newResult);
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

    @GetMapping("/listForStudent")
    public String listQuizesAsResults(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DBUserDetails details = (DBUserDetails) auth.getPrincipal();
        Student currentStudent = (Student) details.getUser();
        List<QuizResult> results = resultService.getResultsByStudent(currentStudent);
        model.addAttribute("results", results);
        model.addAttribute("currDate", LocalDate.now());
        return "student/listQuizesForStudent";
    }

    @GetMapping("/startQuiz")
    public String startQuiz(@RequestParam Long quizID, Model model)
    {
        tempQuiz = (TeacherQuiz) quizService.showSafeByID(quizID);
        tempExercises = exerciseService.getByQuiz(tempQuiz.getId());
        for (Exercise exercise:tempExercises)
        {
            if(exercise instanceof ABCDExercise)
                tempABCDExercises.add((ABCDExercise) exercise);
        }
        return "student/quizStart";
    }

    @GetMapping("/solveABCD")
    public String solveABDC(@RequestParam int exerciseID,Model model)
    {
        if(exerciseID<tempABCDExercises.size())
        {
            model.addAttribute("exercise", tempABCDExercises.get(exerciseID));
            exerciseID++;
            model.addAttribute("counter", exerciseID);
            model.addAttribute("numberOfExercises",tempABCDExercises.size());
            return "student/solveABCD";
        }

        return "student/quizFinish";
    }
}
