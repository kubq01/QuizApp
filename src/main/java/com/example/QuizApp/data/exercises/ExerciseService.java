package com.example.QuizApp.data.exercises;

import com.example.QuizApp.data.exercises.enums.ExerciseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Exercise getExerciseForStudent(Long ID)
    {
        Optional<Exercise> exercise = repo.findById(ID);
        if(exercise.isPresent())
        {
            if(exercise.get() instanceof ABCDExercise)
            {
                ((ABCDExercise) exercise.get()).hideCorrectAnswer();
            }

            return exercise.get();
        }

        return null;
    }

    public Exercise getExerciseByID(Long ID)
    {
        Optional<Exercise> exercise = repo.findById(ID);
        if(exercise.isPresent())
        {
            return exercise.get();
        }

        return null;
    }

    public void deleteAll()
    {
        repo.deleteAll();
    }

    public List<Exercise> getByQuiz(Long ID)
    {
        return repo.findByQuizID(ID);
    }
}
