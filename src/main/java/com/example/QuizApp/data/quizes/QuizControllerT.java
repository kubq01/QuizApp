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
import com.example.QuizApp.data.users.User;
import com.example.QuizApp.data.wrappers.ExerciseAnswerWrapper;
import com.example.QuizApp.data.wrappers.QuizExerciseWrapper;
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
    private StudentQuiz tempStudentQuiz;
    private int exerciseCounter = -1;

    private QuizResult tempQuizResult;

    private AnswerService answerService;

    private List<String> answers;
    private List<String> studentAnswers;
    private QuizResult tempResult;




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
        Teacher currentTeacher = (Teacher) getCurrentUser();
        model.addAttribute("quizList", quizService.showAllByTeacher(currentTeacher));
        return "teacher/listQuizes";
    }

    @GetMapping("/listByS")
    public String listQuizesByCurrentStudent(Model model)
    {
        Student currentStudent = (Student) getCurrentUser();
        model.addAttribute("quizList", quizService.showAllByStudent(currentStudent));
        return "student/listStudentQuizes";
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

    @GetMapping("/addStudentQuiz")
    public String showAddStudentQuiz(
            @RequestParam(name="reset", required = false, defaultValue = "false")
                                         Boolean reset,Model model){
        if (reset){
            tempExercises.clear();
            tempId = 0L;
        }
        model.addAttribute("newQuiz", new TeacherQuiz());
        model.addAttribute("exercises", tempExercises);
        return "student/addStudentQuiz";
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
        Teacher currentTeacher = (Teacher) getCurrentUser();
        Class studentsClass;
        try {
            studentsClass = classService.getClassByIdAndTeacher(Long.parseLong(code), currentTeacher);
            if (studentsClass == null){
                throw new NumberFormatException("shh, it works");
            }
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
        tempExercises.clear();
        return "redirect:/quiz/listByT";
    }

    @PostMapping("/addStudentQuiz/new")
    public String addStudentQuiz(@Valid @ModelAttribute("newQuiz") StudentQuiz newQuiz,
                                 BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("classErr", false);
            model.addAttribute("exercises", tempExercises);
            return"student/addStudentQuiz";
        }
        Student currentStudent = (Student) getCurrentUser();
        newQuiz.setStudent(currentStudent);
        StudentQuiz addedQuiz = (StudentQuiz) quizService.insert(newQuiz);
        for (Exercise exercise:tempExercises) {
            exercise.setQuiz(addedQuiz);
            exerciseService.insert(exercise);
        }
        tempExercises.clear();
        return "redirect:/quiz/listByS";
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
        switch(getCurrentUser().getClass().getSimpleName()){
            case "Student":
                return "redirect:/quiz/addStudentQuiz";
            case "Teacher":
                return "redirect:/quiz/addTeacherQuiz";
            default:
                return "redirect:/logout";
        }
    }

    @GetMapping("/listForStudent")
    public String listQuizesAsResults(Model model){
        Student currentStudent = (Student) getCurrentUser();
        List<QuizResult> results = resultService.getResultsByStudent(currentStudent);
        model.addAttribute("results", results);
        model.addAttribute("currDate", LocalDate.now());
        return "student/listQuizesForStudent";
    }

    @GetMapping("/startQuiz")
    public String startQuiz(@RequestParam Long quizID, Model model)
    {
        Student currentStudent = (Student) getCurrentUser();
        tempQuiz = (TeacherQuiz) quizService.showSafeByID(quizID);
        tempABCDExercises.clear();
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
            model.addAttribute("answerErr", false);
            return "student/solveABCD";
        }

        return "redirect:/quiz/finishQuiz";
    }

    @PostMapping("/sendABCD")
    public String sendABCD(
            @RequestParam(name = "studentAnswer",
                    required = false, defaultValue = "0") int studentAnswer,
            Model model, RedirectAttributes redirectAttributes)
    {
        if (studentAnswer == 0){
            model.addAttribute("exercise", tempABCDExercises.get(exerciseCounter));
            model.addAttribute("counter", exerciseCounter+1);
            model.addAttribute("numberOfExercises",tempABCDExercises.size());
            model.addAttribute("answerErr", true);
            return "student/solveABCD";
        }
        ABCDExercise tempExercise = (ABCDExercise) exerciseService
                .getExerciseByID(tempExercises.get(exerciseCounter).getId());
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
        tempQuizResult.setPointsMax(tempABCDExercises.size());
        tempQuizResult.finishQuiz();
        resultService.insert(tempQuizResult);
        model.addAttribute("result", tempQuizResult);
        return "student/quizFinish";
    }

    /////
    @GetMapping("/startStudentQuiz")
    public String startStudentQuiz(@RequestParam Long quizID, Model model)
    {
        Student currentStudent = (Student) getCurrentUser();
        tempStudentQuiz = (StudentQuiz) quizService.showSafeByID(quizID);
        tempABCDExercises.clear();
        tempExercises = exerciseService.getByQuiz(tempStudentQuiz.getId());
        for (Exercise exercise:tempExercises)
        {
            if(exercise instanceof ABCDExercise)
                tempABCDExercises.add((ABCDExercise) exercise);
        }
        tempQuizResult = new QuizResult(null,null,null);
        return "student/quizStudentStart";
    }

    @GetMapping("/solveStudentABCD")
    public String solveStudentABDC(@RequestParam int exerciseID,Model model)
    {
        if(exerciseID<tempABCDExercises.size())
        {
            model.addAttribute("exercise", tempABCDExercises.get(exerciseID));
            exerciseCounter = exerciseID;
            exerciseID++;
            model.addAttribute("counter", exerciseID);
            model.addAttribute("numberOfExercises",tempABCDExercises.size());
            model.addAttribute("answerErr", false);
            return "student/solveStudentABCD";
        }

        return "redirect:/quiz/finishStudentQuiz";
    }

    @PostMapping("/sendStudentABCD")
    public String sendStudentABCD(
            @RequestParam(name = "studentAnswer",
                    required = false, defaultValue = "0") int studentAnswer,
            Model model, RedirectAttributes redirectAttributes)
    {
        if (studentAnswer == 0){
            model.addAttribute("exercise", tempABCDExercises.get(exerciseCounter));
            model.addAttribute("counter", exerciseCounter+1);
            model.addAttribute("numberOfExercises",tempABCDExercises.size());
            model.addAttribute("answerErr", true);
            return "student/solveStudentABCD";
        }
        ABCDExercise tempExercise = (ABCDExercise) exerciseService
                .getExerciseByID(tempExercises.get(exerciseCounter).getId());

        if (studentAnswer == tempExercise.getCorrectAnswer())
            tempQuizResult.addPoints(tempExercise.getPoints());

        exerciseCounter++;

        redirectAttributes.addAttribute("exerciseID", exerciseCounter);
        return "redirect:/quiz/solveStudentABCD";

        /*
        model.addAttribute("exercise", tempABCDExercises.get(exerciseCounter));
        model.addAttribute("counter", (exerciseCounter + 1));
        model.addAttribute("numberOfExercises",tempABCDExercises.size());

         */

    }

    @GetMapping("/finishStudentQuiz")
    public String finishStudentQuiz(Model model)
    {
        tempQuizResult.setPointsMax(tempABCDExercises.size());
        tempQuizResult.finishQuiz();
        model.addAttribute("result", tempQuizResult);
        return "student/quizStudentFinish";
    }
    /////

    @GetMapping("/viewQuiz")
    public String viewQuiz(@RequestParam("quizID") Long quizID, Model model)
    {
        Student currentStudent = (Student) getCurrentUser();
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


                ExerciseAnswerWrapper wrapper =
                        new ExerciseAnswerWrapper(exercise, answer,
                                studentAnswerString, ((ABCDExercise) exercise).getCorrectAnswer(),
                                studentAnswer.getUserAnswer());
                wrapperList.add(wrapper);
            }


        }

        model.addAttribute("result", resultService.getByQuizIDAndStudentID(tempQuiz.getId(),currentStudent.getId()));

        model.addAttribute("wrapperList", wrapperList);

        tempExercises.clear();
        return "student/viewQuiz";

    }

    @GetMapping("/viewQuizT")
    public String viewQuizT(@RequestParam("quizID") Long quizID, Model model)
    {
        TeacherQuiz quiz = (TeacherQuiz) quizService.showSafeByID(quizID);
        List<Exercise> exerciseList = exerciseService.getByQuiz(quizID);
        List<ABCDExercise> ABCDExercises = new ArrayList<>();

        for(Exercise exercise : exerciseList)
        {
            if(exercise instanceof ABCDExercise)
                ABCDExercises.add((ABCDExercise) exercise);
        }

        model.addAttribute("quiz", quiz);
        model.addAttribute("exercises", ABCDExercises);

        return "teacher/viewQuizForTeacher";
    }

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DBUserDetails details = (DBUserDetails) auth.getPrincipal();
        return details.getUser();
    }
}
