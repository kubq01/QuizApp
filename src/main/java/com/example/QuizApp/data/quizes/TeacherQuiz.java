package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.Class.Class;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.users.Teacher;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "teacher_quiz")
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

    //todo zamienic na localdate
    @Temporal(TemporalType.DATE)
    @Column(name = "startTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    private Integer quizTimeInMinutes;

    @ManyToOne
    @JoinColumn(name = "idT")
    private Teacher teacher;

    private String subject;

    @ManyToOne
    @JoinColumn(name = "students_class_id")
    private Class studentsClass;


    public TeacherQuiz(Boolean countsToAvg, Teacher teacher, String subject, Class studentClass) {
        this.countsToAvg = countsToAvg;
        this.teacher = teacher;
        this.subject = subject;
        this.studentsClass = studentClass;
    }


    public TeacherQuiz(Boolean countsToAvg, Date startTime, Integer quizTimeInMinutes,
                       Teacher teacher, String subject, Class studentsClass) {

        super();
        this.countsToAvg = countsToAvg;
        this.startTime = startTime;
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
