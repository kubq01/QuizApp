package com.example.QuizApp.data.wrappers;

import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.quizes.Quiz;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class QuizExerciseWrapper {
    private List<Exercise> exerciseList;
    private Quiz quiz;

    private List<String> answers = new ArrayList<>();

    public QuizExerciseWrapper(List<Exercise> exerciseList, Quiz quiz) {
        this.exerciseList = exerciseList;
        this.quiz = quiz;

        for(Exercise exercise: exerciseList)
        {
            if(exercise instanceof ABCDExercise)
            {
                short ans = ((ABCDExercise) exercise).getCorrectAnswer();

                switch(ans)
                {
                    case 1: answers.add("A"); break;
                    case 2: answers.add("B"); break;
                    case 3: answers.add("C"); break;
                    case 4: answers.add("D"); break;
                    default: answers.add("ERROR"); break;
                }
            }
        }
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
