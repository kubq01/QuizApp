package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.WrittenExercise;
import com.example.QuizApp.data.users.Student;
import com.example.QuizApp.data.users.Teacher;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "TeacherQuiz")
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("TEACHER")
public class TeacherQuiz extends Quiz {

    /*
    True if tests mark counts to students evaluation by avg
    (used for printing students marks)
     */
    private Boolean countsToAvg;

    private LocalDateTime start;
    private LocalDateTime end;
    private Integer quizTimeInMinutes;

    @ManyToOne
    @JoinColumn(name = "id", insertable=false, updatable=false)
    private Teacher teacher;

    private String subject;


    public TeacherQuiz(Boolean countsToAvg, Teacher teacher, String subject) {
        this.countsToAvg = countsToAvg;
        this.teacher = teacher;
        this.subject = subject;
    }

    public TeacherQuiz(Set<Exercise> exercises,
                       Boolean countsToAvg, LocalDateTime start,
                       LocalDateTime end, Integer quizTimeInMinutes,
                       Teacher teacher, String subject) {
        super(exercises);
        this.countsToAvg = countsToAvg;
        this.start = start;
        this.end = end;
        this.quizTimeInMinutes = quizTimeInMinutes;
        this.teacher = teacher;
        this.subject = subject;
    }
}
