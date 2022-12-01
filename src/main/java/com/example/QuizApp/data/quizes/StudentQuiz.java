package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.WrittenExercise;
import com.example.QuizApp.data.users.Student;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "studentQuiz")
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("STUDENT")
public class StudentQuiz extends Quiz {

    public StudentQuiz(Set<Exercise> exercises) {
        super(exercises);
    }
}
