package com.example.QuizApp.data.wrappers;

import com.example.QuizApp.data.exercises.Exercise;

public class ExerciseAnswerWrapper {

    private Exercise exercise;
    private String answer;
    private String studentAnswer;

    private Short correctS;

    private Short studentS;

    public Exercise getExercise() {
        return exercise;
    }

    public String getAnswer() {
        return answer;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public short getCorrectS() {
        return correctS;
    }

    public short getStudentS() {
        return studentS;
    }

    public ExerciseAnswerWrapper(Exercise exercise, String answer,
                                 String studentAnswer, Short correctS,
                                 Short studentS) {
        this.exercise = exercise;
        this.answer = answer;
        this.studentAnswer = studentAnswer;
        this.correctS = correctS;
        this.studentS = studentS;
    }
}
