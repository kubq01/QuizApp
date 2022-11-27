package com.example.QuizApp.data.exercises;

import com.example.QuizApp.data.quizes.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/exercise")
public class ExerciseController {
    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Exercise> getAllExercises(){
        return exerciseService.showAll();
    }

    @GetMapping("/type")
    @ResponseBody
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

    @PostMapping("/insert")
    @ResponseBody
    public Exercise insertExercise(@RequestBody Exercise newExercise){
        exerciseService.insert(newExercise);
        return newExercise;
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public Boolean deleteExercise(@RequestParam(name = "id") Long id){
        return exerciseService.deleteById(id);
    }
}
