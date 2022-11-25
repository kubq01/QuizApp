package com.example.QuizApp.data.exercises;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Exercise {    //TODO sposób na spisywanie poprawnych odpowiedzi i połączenie z klasą Quiz

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String question;
    private int points;
    private boolean correct;    //use Boolean instead? three-state?

}
