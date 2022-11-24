package com.example.QuizApp.data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class ABCDExecise extends Execise{

    String correctAnswer;

    public ABCDExecise() {
    }

    public ABCDExecise(String question, String studentAnswer, int points, int quizId, String correctAnswer) {
        super(question, studentAnswer,points,quizId);
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
