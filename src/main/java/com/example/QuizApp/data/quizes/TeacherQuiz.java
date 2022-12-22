package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.Class.Class;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.users.Teacher;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Temporal(TemporalType.DATE)
    @Column(name = "startTime")
    private Date startTime;
    @Temporal(TemporalType.DATE)
    @Column(name = "deadline")
    private Date deadline;

    private Integer quizTimeInMinutes;

    @ManyToOne
    @JoinColumn(name = "idT")
    private Teacher teacher;

    private String subject;

    @ManyToOne
    @JoinColumn(name = "students_class_id")
    private Class studentsClass;

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }


    public TeacherQuiz(Boolean countsToAvg, Teacher teacher, String subject, Class studentClass) {
        this.countsToAvg = countsToAvg;
        this.teacher = teacher;
        this.subject = subject;
        this.studentsClass = studentClass;
    }


    public TeacherQuiz(Boolean countsToAvg, LocalDateTime start,
                       LocalDateTime end, Integer quizTimeInMinutes,
                       Teacher teacher, String subject, Class studentsClass) {

        super();
        this.countsToAvg = countsToAvg;
        /*this.start = start;
        this.end = end;*/
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
