package com.example.QuizApp.data.exercises;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "exerciseType")
@JsonSubTypes({
        @JsonSubTypes.Type(value=ABCDExercise.class, name = "ABCD"),
        @JsonSubTypes.Type(value=WrittenExercise.class, name = "Written")
})
public abstract class Exercise {    //TODO sposób na spisywanie poprawnych odpowiedzi i połączenie z klasą Quiz, dodalem klucz obcy zeby polaczyc, poprawne odp
        //TODO poprawna odp w abcd jest dana, w opisowych nauczyciel sam ocenia indywidualnie kazda odpowiedz i dodaje recznie punkty wg uznania (tak jak na np testportalu) - nie mozna tego zrobic programowo
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String question;
    private int points;
    private boolean correct;    //use Boolean instead? three-state?

}
