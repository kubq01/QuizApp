package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuizService {

    private final QuizRepository repo;

    @Autowired
    public QuizService(QuizRepository repo) {
        this.repo = repo;
    }


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

    public Boolean deleteById(Long id) {
        repo.deleteById(id);
        return true;
    }
}
