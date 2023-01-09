package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.users.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "studentQuiz")
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("STUDENT")
public class StudentQuiz extends Quiz {

    @NotEmpty(message = "Quiz musi mieć podane zagadnienie.")
    private String subject;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentQuiz(Long id, String subject) {
        super(id);
        this.subject = subject;
    }

    public StudentQuiz(String subject, Student student) {
        this.subject = subject;
        this.student = student;
    }
}
