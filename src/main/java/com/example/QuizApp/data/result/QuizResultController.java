package com.example.QuizApp.data.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz/results")
public class QuizResultController {

    private final QuizResultService service;

    @Autowired
    public QuizResultController(QuizResultService service){
        this.service = service;
    }

    @GetMapping("/list")
    public List<QuizResult> getAll()
    {
        return service.getAll();
    }

    @GetMapping("/quizID")
    public List<QuizResult> getAllQuizesByID(@RequestParam(name = "quizID") Long quizID){
        try {
            return service.getByQuizID(quizID);
        }
        catch (IllegalArgumentException exception){
            exception.printStackTrace();
            return List.of();   //TODO przerobić na zwrócenie 400 Bad Request
        }
    }

    @GetMapping("/studentID")
    public QuizResult getQuizeByStudentID(@RequestParam(name = "quizID") Long quizID, @RequestParam(name = "studentID") Long studentID){
        try {
            return service.getByQuizIDAndStudentID(quizID,studentID);
        }
        catch (IllegalArgumentException exception){
            exception.printStackTrace();
            return null;   //TODO przerobić na zwrócenie 400 Bad Request
        }
    }

    @PostMapping("/insert")
    public QuizResult insertResult(@RequestBody QuizResult newQuizResult){
        service.insert(newQuizResult);
        return newQuizResult;
    }
}
