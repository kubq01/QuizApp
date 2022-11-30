package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.WrittenExercise;
import com.example.QuizApp.data.users.Student;
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
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "quizType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StudentQuiz.class, name = "Students"),
        @JsonSubTypes.Type(value = TeacherQuiz.class, name = "Teachers")
})
public abstract class Quiz {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int mark;
    @ManyToOne
    @JoinColumn(name = "id", insertable=false, updatable=false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "id", insertable=false, updatable=false)
    private ABCDExercise abcdExercise;

    @ManyToOne
    @JoinColumn(name = "id", insertable=false, updatable=false)
    private WrittenExercise writtenExercise;

}
