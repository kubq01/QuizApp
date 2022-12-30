package com.example.QuizApp.data.exercises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class ExerciseControllerT {
    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseControllerT(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public String showIndex(){
        return "index";
    }

    @GetMapping("/get")
    public String showSelect(Model model){
        model.addAttribute("id", "");
        return "selectWritten";
    }

    @GetMapping("/get/id")
    public String showQuestion(@RequestParam(name = "id") Long id, Model model){
        WrittenExercise exercise = (WrittenExercise) exerciseService.getExerciseForStudent(id);
        model.addAttribute("question", exercise.getQuestion());
        model.addAttribute("id", exercise.getId());
        return "showWritten";
    }

    @GetMapping("/insert")
    public String showInsert(Model model){
        model.addAttribute("newWritten", new WrittenExercise());
        return "addWritten";
    }

    @PostMapping("/insert/new")
    public String insertExercise(@ModelAttribute WrittenExercise newExercise){
        newExercise.setPoints(1);
        exerciseService.insert(newExercise);
        return "redirect:/insert";
    }

}
