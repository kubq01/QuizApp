package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.WrittenExercise;
import com.example.QuizApp.data.users.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "studentQuiz")
@Data
@NoArgsConstructor
@DiscriminatorValue("STUDENT")
public class StudentQuiz extends Quiz {

    public StudentQuiz(int id, int mark, Student student, ABCDExercise abcdExercise, WrittenExercise writtenExercise) {
        super(id, mark, student,abcdExercise, writtenExercise);
    }
}
