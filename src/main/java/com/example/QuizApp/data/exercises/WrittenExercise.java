package com.example.QuizApp.data.exercises;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "WrittenExercise")
@Data
@NoArgsConstructor
@DiscriminatorValue("WRITTEN")
public class WrittenExercise extends Exercise {

    private String answer;
    private int pointsGained;

    public WrittenExercise(Long id, String question, int points, boolean correct, String answer, int pointsGained) {
        super(id, question, points, correct);
        this.answer = answer;
        this.pointsGained = pointsGained;
    }
}
