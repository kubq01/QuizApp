package com.example.QuizApp.data.result;

import com.example.QuizApp.data.quizes.Quiz;
import com.example.QuizApp.data.quizes.TeacherQuiz;
import com.example.QuizApp.data.quizes.enums.QuizStatus;
import com.example.QuizApp.data.users.Student;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "QuizResult")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private Float mark;
    private Boolean attended;
    private Integer pointsGained;

    private Integer pointsMax;


    @ManyToOne
    @JoinColumn(name = "teacher_quiz_id")
    private TeacherQuiz teacherQuiz;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public QuizResult( Integer pointsMax, TeacherQuiz teacherQuiz, Student student) {
        this.mark = null;
        this.attended = false;
        this.pointsGained = 0;
        this.pointsMax = pointsMax;
        this.teacherQuiz = teacherQuiz;
        this.student = student;
    }

    public void addPoints(int points)
    {
        pointsGained += points;
    }

    public void finishQuiz()
    {
        mark = pointsGained*1.0F/pointsMax*100;
        attended = true;
    }


}
