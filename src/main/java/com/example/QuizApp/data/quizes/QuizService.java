package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.ExerciseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    public QuizService(QuizRepository repo) {
        this.repo = repo;

    }

    QuizRepository repo;

    public List<Quiz> showAll()
    {
       return repo.findAll();
    }

    public void insert(Quiz quiz)
    {
       repo.save(quiz);
    }

    public List<Quiz> showByType(QuizType type)
    {
        return repo.findByType(type.name());
    }


}
