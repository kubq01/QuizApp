package com.example.QuizApp.data;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class LongExecise extends Execise{

    public LongExecise() {
    }

    public LongExecise(String question, String studentAnswer, int points, int quizId) {
        super(question, studentAnswer, points, quizId);
    }

}
