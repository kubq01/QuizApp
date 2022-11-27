package com.example.QuizApp.data.exercises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {
    private ExerciseRepository repo;

    @Autowired
    public ExerciseService(ExerciseRepository repo) {
        this.repo = repo;
    }

    public List<Exercise> showAll()
    {
        return repo.findAll();
    }

    public void insert(Exercise exercise)
    {
        repo.save(exercise);
    }

    public List<Exercise> showByType(ExerciseType type)
    {
        return repo.findByType(type.name());
    }

    public Boolean deleteById(Long id){
        repo.deleteById(id);
        return true;
    }
}
