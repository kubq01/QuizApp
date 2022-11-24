package com.example.QuizApp.data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class StudentQuiz extends Quiz {

    int studentId;

    public StudentQuiz() {
    }

    public StudentQuiz(int id, int mark, int studentId) {
        super(id, mark);
        this.studentId = studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }


}
