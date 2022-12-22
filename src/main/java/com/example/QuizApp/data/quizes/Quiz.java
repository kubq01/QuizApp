package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.WrittenExercise;
import com.example.QuizApp.data.users.Teacher;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="descriminatorColumn")
@Getter
@Setter
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
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(orphanRemoval = true)
    private Set<Exercise> exercises = new LinkedHashSet<>();

  //  @OneToMany(mappedBy = "quiz", orphanRemoval = true)
   // private Set<QuizResult> quizResults = new LinkedHashSet<>();

    public Quiz(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void hideExercises()
    {
        exercises = null;
    }


}
