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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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

    @NotNull(message = "Quiz musi mieć wybraną datę.")
    @Column(name = "start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    @NotNull(message = "Należy podać czas trwania quizu.")
    @Min(value = 15, message = "Czas na pisanie nie może być mniejszy niż 15 minut.")
    private Integer quizTimeInMinutes;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idT", nullable = true)
    private Teacher teacher;

    @NotEmpty(message = "Quiz musi mieć podane zagadnienie.")
    private String subject;


    @ManyToOne
    @JoinColumn(name = "students_class_id")
    private Class studentsClass;

    public TeacherQuiz(Boolean countsToAvg, Teacher teacher, String subject, Class studentClass) {
        this.countsToAvg = countsToAvg;
        this.teacher = teacher;
        this.subject = subject;
        this.studentsClass = studentClass;
        this.startTime = LocalDate.now();
    }


    public TeacherQuiz(Boolean countsToAvg, LocalDate startTime, Integer quizTimeInMinutes,
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
        this.teacher = null;
    }
}
