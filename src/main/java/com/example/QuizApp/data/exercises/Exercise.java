package com.example.QuizApp.data.exercises;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="descriminatorColumn")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Exercise {    //TODO sposób na spisywanie poprawnych odpowiedzi i połączenie z klasą Quiz, dodalem klucz obcy zeby polaczyc, poprawne odp
        //TODO poprawna odp w abcd jest dana, w opisowych nauczyciel sam ocenia indywidualnie kazda odpowiedz i dodaje recznie punkty wg uznania (tak jak na np testportalu) - nie mozna tego zrobic programowo
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String question;
    private int points;
    private boolean correct;    //use Boolean instead? three-state?

}
