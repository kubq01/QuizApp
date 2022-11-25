package com.example.QuizApp.data.exercises;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "WrittenExercise")
@Data
@NoArgsConstructor
public class WrittenExercise extends Exercise {

    private String answer;
    private int pointsGained;

    public WrittenExercise(int id, String question, int points, boolean correct, String answer, int pointsGained) {
        super(id, question, points, correct);
        this.answer = answer;
        this.pointsGained = pointsGained;
    }
}
