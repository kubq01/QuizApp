package com.example.QuizApp.data.result;

import com.example.QuizApp.data.quizes.Quiz;
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
    private QuizStatus status;
    private Integer pointsGained;


    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public QuizResult(Float mark, QuizStatus status, Integer pointsGained, Quiz quiz, Student student) {
        this.mark = mark;
        this.status = status;
        this.pointsGained = pointsGained;
        this.quiz = quiz;
        this.student = student;
    }
}
