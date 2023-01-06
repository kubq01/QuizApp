package com.example.QuizApp.data.exercises;

import com.example.QuizApp.data.quizes.Quiz;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ABCDExercise")
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("ABCD")
public class ABCDExercise extends Exercise {

    @NotEmpty(message = "Odpowiedź nie może być pusta")
    private String answerA;
    @NotEmpty(message = "Odpowiedź nie może być pusta")
    private String answerB;
    @NotEmpty(message = "Odpowiedź nie może być pusta")
    private String answerC;
    @NotEmpty(message = "Odpowiedź nie może być pusta")
    private String answerD;
    @NotNull(message = "Jedna z odpowiedzi musi zostać zaznaczona.")
    private Short correctAnswer;

    public ABCDExercise(String question, Quiz quiz, String answerA, String answerB, String answerC, String answerD, Short correctAnswer) {
        super(question, quiz);
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
    }

    public void hideCorrectAnswer() {
        this.correctAnswer = -1;
    }

    @Override
    public String myType() {
        return "ABCD";
    }
}
