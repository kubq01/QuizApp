/*
package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.ExerciseService;
import com.example.QuizApp.data.wrappers.QuizExerciseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;
    private final ExerciseService exerciseService;

    @Autowired
    public QuizController(QuizService service, ExerciseService exerciseService){
        this.quizService = service;
        this.exerciseService = exerciseService;
    }

    @PostMapping("/teacher/add")
    public TeacherQuiz addTeacherQuiz(@RequestBody QuizExerciseWrapper wrapper){
        TeacherQuiz newQuiz = (TeacherQuiz) wrapper.getQuiz();
        List<Exercise> exercises = wrapper.getExerciseList();
        TeacherQuiz addedQuiz = (TeacherQuiz) quizService.insert(newQuiz);
        //TODO Jeśli nie będzie działać to prawdopodobnie przez to, że quiz nie ma id, wymaga sprawdzenia.
        for (Exercise newExercise: exercises){
            newExercise.setQuiz(addedQuiz);
            exerciseService.insert(newExercise);
        }
        return newQuiz;
    }

    @GetMapping("/list")
    public List<Quiz> getAllQuizes(){
        return quizService.showAll();
    }

    @GetMapping("/type")
    public List<Quiz> getAllQuizesByType(@RequestParam(name = "type") String quizType){
        try {
            QuizType type = QuizType.valueOf(quizType);
            return quizService.showByType(type);
        }
        catch (IllegalArgumentException exception){
            exception.printStackTrace();
            return List.of();   //TODO przerobić na zwrócenie 400 Bad Request
        }
    }

    @GetMapping("/class")
    public List<Quiz> getByClassId(@RequestParam(name = "class") int classID)
    {
        try {
            return quizService.showByClass(classID);
        }
        catch (IllegalArgumentException exception){
            exception.printStackTrace();
            return List.of();   //TODO przerobić na zwrócenie 400 Bad Request
        }
    }

    @GetMapping("/quizID")
    public Quiz getSafeByID(@RequestParam(name = "quizID") Long quizID)
    {
        try {
            return quizService.showSafeByID(quizID);
        }
        catch (IllegalArgumentException exception){
            exception.printStackTrace();
            return null;   //TODO przerobić na zwrócenie 400 Bad Request
        }
    }

    @PostMapping("/insert")
    public Quiz insertQuiz(@RequestBody Quiz newQuiz){
        quizService.insert(newQuiz);
        return newQuiz;
    }

    @DeleteMapping("/delete")
    public Boolean deleteQuiz(@RequestParam(name = "id") Long id){
        return quizService.deleteById(id);
    }
}
*/
