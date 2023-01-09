package com.example.QuizApp.data.result;

import com.example.QuizApp.data.quizes.TeacherQuiz;
import com.example.QuizApp.data.users.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    private Boolean countsToAvg;


    @ManyToOne
    @JoinColumn(name = "teacher_quiz_id")
    private TeacherQuiz teacherQuiz;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;

    public void addPoints(int points)
    {
        pointsGained += points;
    }

    public void finishQuiz()
    {
        mark = pointsGained*1.0F/pointsMax*100;
        attended = true;
    }

    public QuizResult(Integer pointsGained) {
        this.pointsGained = pointsGained;
    }
}
