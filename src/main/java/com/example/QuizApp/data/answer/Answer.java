package com.example.QuizApp.data.answer;

import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.result.QuizResult;
import com.example.QuizApp.data.users.Student;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    /*
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

     */

    @ManyToOne
    @JoinColumn(name = "quiz_result_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuizResult quizResult;

    @NotNull
    private Short userAnswer;

    public Answer(Exercise exercise, QuizResult quizResult, Short userAnswer) {
        this.exercise = exercise;
        this.quizResult = quizResult;
        this.userAnswer = userAnswer;
    }
}
