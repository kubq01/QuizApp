package com.example.QuizApp.data.exercises;


import com.example.QuizApp.data.quizes.Quiz;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "WrittenExercise")
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("WRITTEN")
public class WrittenExercise extends Exercise {

    private String answer;

    public WrittenExercise(Quiz quiz, String question, Integer points, Integer pointsGained, String answer) {
        super(question, points, pointsGained, quiz);
        this.answer = answer;
    }
}
