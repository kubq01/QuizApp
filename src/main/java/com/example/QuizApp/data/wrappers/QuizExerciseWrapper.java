package com.example.QuizApp.data.wrappers;

import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.quizes.Quiz;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class QuizExerciseWrapper {
    private List<Exercise> exerciseList;
    private Quiz quiz;
}
