/*
package com.example.QuizApp.data.exercises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/list")
    public List<Exercise> getAllExercises(){
        return exerciseService.showAll();
    }

    @GetMapping("/type")
    public List<Exercise> getExercisesByType(@RequestParam(name = "type") String exerciseType){
        try {
            ExerciseType type = ExerciseType.valueOf(exerciseType);
            return exerciseService.showByType(type);
        }
        catch (IllegalArgumentException exception){
            exception.printStackTrace();
            return List.of();   //TODO przerobić na zwrócenie 400 Bad Request
        }
    }

    @GetMapping("/{ID")
    public Exercise getExerciseForStudent(@RequestParam(name = "ID") Long id)
    {
        Exercise exercise = exerciseService.getExerciseForStudent(id);
        if(exercise!=null)
        {
            return exercise;
        }else {
            return null; //TODO: przerobic na error
        }
    }

    @PostMapping("/insert")
    public Exercise insertExercise(@RequestBody Exercise newExercise){
        exerciseService.insert(newExercise);
        return newExercise;
    }

    @DeleteMapping("/delete")
    public Boolean deleteExercise(@RequestParam(name = "id") Long id){
        return exerciseService.deleteById(id);
    }

    //For now we don't modify exercises
}
*/
