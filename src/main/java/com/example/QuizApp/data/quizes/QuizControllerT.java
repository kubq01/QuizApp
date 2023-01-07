package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.Class.Class;
import com.example.QuizApp.data.Class.ClassService;
import com.example.QuizApp.data.answer.Answer;
import com.example.QuizApp.data.answer.AnswerService;
import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.ExerciseService;
import com.example.QuizApp.data.result.QuizResult;
import com.example.QuizApp.data.result.QuizResultService;
import com.example.QuizApp.data.users.DBUserDetails;
import com.example.QuizApp.data.users.Student;
import com.example.QuizApp.data.users.Teacher;
import com.example.QuizApp.data.wrappers.ExerciseAnswerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.sql.SQLDataException;
import java.sql.SQLException;
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

    private QuizResult tempQuizResult;

    private AnswerService answerService;

    private List<String> answers;
    private List<String> studentAnswers;




    @Autowired
    public QuizControllerT(QuizService service, ExerciseService exerciseService, QuizResultService resultService,
                           ClassService classService, AnswerService answerService){
        this.quizService = service;
        this.exerciseService = exerciseService;
        this.resultService = resultService;
        this.classService = classService;
        tempExercises = new ArrayList<>();
        this.answerService = answerService;
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
        model.addAttribute("classErr", false);
        return "teacher/addQuiz";
    }

    @PostMapping("/addTeacherQuiz/new")
    public String addTeacherQuiz(@Valid @ModelAttribute("newQuiz") TeacherQuiz quiz,
                                 BindingResult result,
                                 @RequestParam @NotEmpty String code,
                                 Model model){
        if (result.hasErrors()){
            model.addAttribute("classErr", false);
            model.addAttribute("exercises", tempExercises);
            return"teacher/addQuiz";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DBUserDetails details = (DBUserDetails) auth.getPrincipal();
        Teacher currentTeacher = (Teacher) details.getUser();
        Class studentsClass;
        try {
            studentsClass = classService.getClassById(Long.parseLong(code));
        } catch (NumberFormatException e){
            model.addAttribute("classErr", true);
            model.addAttribute("exercises", tempExercises);
            return"teacher/addQuiz";
        }
        quiz.setTeacher(currentTeacher);
        quiz.setStudentsClass(studentsClass);
        try {
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
        } catch (DataIntegrityViolationException e){
            model.addAttribute("classErr", true);
            model.addAttribute("exercises", tempExercises);
            return"teacher/addQuiz";
        }

        return "redirect:/quiz/listByT";
    }



    @GetMapping("/addABCD")
    public String showAddABCD(Model model){
        model.addAttribute("newABCD", new ABCDExercise());
        return "teacher/addABCD";
    }

    @PostMapping("/addABCD/new")
    public String addABCD(@Valid @ModelAttribute("newABCD") ABCDExercise exercise , BindingResult result, Model model){
        if(result.hasErrors()){
            return "teacher/addABCD";
        }
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DBUserDetails details = (DBUserDetails) auth.getPrincipal();
        Student currentStudent = (Student) details.getUser();
        tempQuiz = (TeacherQuiz) quizService.showSafeByID(quizID);
        tempExercises = exerciseService.getByQuiz(tempQuiz.getId());
        for (Exercise exercise:tempExercises)
        {
            if(exercise instanceof ABCDExercise)
                tempABCDExercises.add((ABCDExercise) exercise);
        }
        tempQuizResult = resultService.getByQuizIDAndStudentID(tempQuiz.getId(), currentStudent.getId());
        return "student/quizStart";
    }

    @GetMapping("/solveABCD")
    public String solveABDC(@RequestParam int exerciseID,Model model)
    {
        if(exerciseID<tempABCDExercises.size())
        {
            model.addAttribute("exercise", tempABCDExercises.get(exerciseID));
            exerciseCounter = exerciseID;
            exerciseID++;
            model.addAttribute("counter", exerciseID);
            model.addAttribute("numberOfExercises",tempABCDExercises.size());

            return "student/solveABCD";
        }

        return "redirect:/quiz/finishQuiz";
    }

    @PostMapping("/sendABCD")
    public String sendABCD(@RequestParam("studentAnswer") int studentAnswer, RedirectAttributes redirectAttributes)
    {
        ABCDExercise tempExercise = (ABCDExercise) exerciseService.getExerciseByID(tempExercises.get(exerciseCounter).getId());
        Answer answer = new Answer(tempExercise,
                tempQuizResult,
                (short) studentAnswer);

        if (studentAnswer == tempExercise.getCorrectAnswer())
            tempQuizResult.addPoints(tempExercise.getPoints());

        answerService.insert(answer);

        exerciseCounter++;

        redirectAttributes.addAttribute("exerciseID", exerciseCounter);
        return "redirect:/quiz/solveABCD";

        /*
        model.addAttribute("exercise", tempABCDExercises.get(exerciseCounter));
        model.addAttribute("counter", (exerciseCounter + 1));
        model.addAttribute("numberOfExercises",tempABCDExercises.size());

         */

    }

    @GetMapping("/finishQuiz")
    public String finishQuiz(Model model)
    {
        tempQuizResult.finishQuiz();
        resultService.insert(tempQuizResult);
        model.addAttribute("result", tempQuizResult);
        return "student/quizFinish";
    }

    @GetMapping("/viewQuiz")
    public String viewQuiz(@RequestParam("quizID") Long quizID, Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DBUserDetails details = (DBUserDetails) auth.getPrincipal();
        Student currentStudent = (Student) details.getUser();
        tempQuiz = (TeacherQuiz) quizService.showSafeByID(quizID);
        tempExercises = exerciseService.getByQuiz(tempQuiz.getId());
        answers = new ArrayList<>();
        List<ExerciseAnswerWrapper> wrapperList = new ArrayList<>();
        for (Exercise exercise:tempExercises)
        {
            if(exercise instanceof ABCDExercise)
            {
                tempABCDExercises.add((ABCDExercise) exercise);
                short ans = ((ABCDExercise) exercise).getCorrectAnswer();
                String answer = "N";
                switch (ans)
                {
                    case 1: answer = "A"; break;
                    case 2: answer = "B"; break;
                    case 3: answer = "C"; break;
                    case 4: answer = "D"; break;

                }

                Answer studentAnswer = answerService.showByExerciseAndQuizResult(exercise,
                        resultService.getByQuizIDAndStudentID(tempQuiz.getId(), currentStudent.getId()));
                ans = studentAnswer.getUserAnswer();
                String studentAnswerString = "N";
                switch (ans)
                {
                    case 1: studentAnswerString = "A"; break;
                    case 2: studentAnswerString = "B"; break;
                    case 3: studentAnswerString = "C"; break;
                    case 4: studentAnswerString = "D"; break;

                }


                ExerciseAnswerWrapper wrapper = new ExerciseAnswerWrapper(exercise,answer,studentAnswerString);
                wrapperList.add(wrapper);
            }


        }

        model.addAttribute("result", resultService.getByQuizIDAndStudentID(tempQuiz.getId(),currentStudent.getId()));

        model.addAttribute("wrapperList", wrapperList);

        tempExercises.clear();
        return "student/viewQuiz";

    }
}
