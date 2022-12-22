package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.Class.Class;
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
    @JoinColumn(name = "idT")
    private Teacher teacher;

    private String subject;

    @ManyToOne
    @JoinColumn(name = "students_class_id")
    private Class studentsClass;


    public TeacherQuiz(Boolean countsToAvg, Teacher teacher, String subject) {
        this.countsToAvg = countsToAvg;
        this.teacher = teacher;
        this.subject = subject;
    }


    public TeacherQuiz(Set<Exercise> exercises,
                       Boolean countsToAvg, LocalDateTime start,
                       LocalDateTime end, Integer quizTimeInMinutes,
                       Teacher teacher, String subject, Class studentsClass) {

        super(exercises);
        this.countsToAvg = countsToAvg;
        this.start = start;
        this.end = end;
        this.quizTimeInMinutes = quizTimeInMinutes;
        this.teacher = teacher;
        this.subject = subject;
        this.studentsClass = studentsClass;
    }

    public void hideTeacher()
    {
        teacher = null;
    }
}
