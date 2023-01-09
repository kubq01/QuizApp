package com.example.QuizApp.data.exercises;

import com.example.QuizApp.data.quizes.Quiz;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotEmpty;

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
    @NotEmpty(message = "Pytanie nie może być puste.")
    private String question;
    private Integer points;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Quiz quiz;

    public Exercise(String question, Quiz quiz) {
        this.question = question;
        this.points = 1;
        this.quiz = quiz;
    }

    public abstract String myType();

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public Integer getPoints() {
        return points;
    }
}
