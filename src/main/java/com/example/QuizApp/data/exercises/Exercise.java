package com.example.QuizApp.data.exercises;

import com.example.QuizApp.data.quizes.Quiz;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="descriminatorColumn")
@Getter
@Setter
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
    private Integer points;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public Exercise(String question, Integer points, Integer pointsGained, Quiz quiz) {
        this.question = question;
        this.points = points;
        this.quiz = quiz;
    }

    public abstract String myType();
}
