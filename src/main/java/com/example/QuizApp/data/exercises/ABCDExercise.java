package com.example.QuizApp.data.exercises;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ABCDExercise")
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("ABCD")
public class ABCDExercise extends Exercise {

    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private Short correctAnswer;
    private Short chosenAnswer;

    public ABCDExercise(Long id, String question,
                        Integer points, String answerA,
                        String answerB, String answerC,
                        String answerD, Short correctAnswer,
                        Short chosenAnswer) {
        super(id, question, points);
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
        this.chosenAnswer = chosenAnswer;
    }
}
