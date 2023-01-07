package com.example.QuizApp.data.wrappers;

import com.example.QuizApp.data.exercises.Exercise;

public class ExerciseAnswerWrapper {

    private Exercise exercise;
    private String answer;
    private String studentAnswer;

    public Exercise getExercise() {
        return exercise;
    }

    public String getAnswer() {
        return answer;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public ExerciseAnswerWrapper(Exercise exercise, String answer, String studentAnswer) {
        this.exercise = exercise;
        this.answer = answer;
        this.studentAnswer = studentAnswer;
    }
}
