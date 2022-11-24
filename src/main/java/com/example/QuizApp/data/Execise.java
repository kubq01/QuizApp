package com.example.QuizApp.data;

import javax.persistence.*;

@MappedSuperclass
public abstract class Execise {

    @Id
    @SequenceGenerator(
            name = "execise_sequence",
            sequenceName = "execise_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "execise_sequence"
    )
    int id;
    String question;
    String studentAnswer;
    int points;
    int quizId;

    public Execise(String question, String studentAnswer, int points,int quizId) {
        this.question = question;
        this.studentAnswer = studentAnswer;
        this.points = points;
        this.quizId = quizId;
    }

    public Execise() {
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
