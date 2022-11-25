package com.example.QuizApp.data.exercises;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ABCDExercise")
@Data
@NoArgsConstructor
public class ABCDExercise extends Exercise {

    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private short correctAnswer;
    private short chosenAnswer;

    public ABCDExercise(int id, String question, int points, boolean correct,
                        String answerA, String answerB, String answerC,
                        String answerD, short correctAnswer, short chosenAnswer) {
        super(id, question, points, correct);
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
        this.chosenAnswer = chosenAnswer;
    }
}
