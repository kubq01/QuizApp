package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.users.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "studentQuiz")
@Data
@NoArgsConstructor
public class StudentQuiz extends Quiz {

    public StudentQuiz(int id, int mark, Student student) {
        super(id, mark, student);
    }
}
