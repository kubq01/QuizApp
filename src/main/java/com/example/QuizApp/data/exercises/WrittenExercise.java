package com.example.QuizApp.data.exercises;


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

    public WrittenExercise(Long id, String question, Integer points, String answer) {
        super(id, question, points);
        this.answer = answer;
    }
}
