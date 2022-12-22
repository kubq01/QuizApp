package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.ExerciseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService service;

    @Autowired
    public QuizController(QuizService service){
        this.service = service;
    }

    @GetMapping("/list")
    public List<Quiz> getAllQuizes(){
        return service.showAll();
    }

    @GetMapping("/type")
    public List<Quiz> getAllQuizesByType(@RequestParam(name = "type") String quizType){
        try {
            QuizType type = QuizType.valueOf(quizType);
            return service.showByType(type);
        }
        catch (IllegalArgumentException exception){
            exception.printStackTrace();
            return List.of();   //TODO przerobić na zwrócenie 400 Bad Request
        }
    }

    @GetMapping("/class")
    public List<Quiz> getQuizesWithoutunnecessaryData(@RequestParam(name = "class") int classID)
    {
        try {
            return service.showWithoutExercises(classID);
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
            return service.showSafeByID(quizID);
        }
        catch (IllegalArgumentException exception){
            exception.printStackTrace();
            return null;   //TODO przerobić na zwrócenie 400 Bad Request
        }
    }

    @PostMapping("/insert")
    public Quiz insertExercise(@RequestBody Quiz newQuiz){
        service.insert(newQuiz);
        return newQuiz;
    }

    @DeleteMapping("/delete")
    public Boolean deleteExercise(@RequestParam(name = "id") Long id){
        return service.deleteById(id);
    }
}
