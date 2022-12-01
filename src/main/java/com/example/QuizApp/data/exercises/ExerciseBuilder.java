package com.example.QuizApp.data.exercises;

import org.springframework.stereotype.Component;

@Component
public class ExerciseBuilder {

    public ABCDExercise makeABCDExercise( String question, int points, int pointsGained, String answerA,
                                         String answerB, String answerC, String answerD, short correctAnswer,
                                          short chosenAnswer){
        return new ABCDExercise(question,points,pointsGained,answerA,answerB,answerC,answerD,correctAnswer,chosenAnswer);
    }

    public WrittenExercise makeWrittenExercise(String question, Integer points, Integer pointsGained, String answer)
    {
        return new WrittenExercise(question,points,pointsGained,answer);
    }
}
