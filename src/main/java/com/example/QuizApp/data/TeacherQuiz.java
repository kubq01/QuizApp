package com.example.QuizApp.data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class TeacherQuiz extends Quiz {

    int teacherId;
    int studentId;


    public TeacherQuiz() {
    }

    public TeacherQuiz(int id, int mark, int teacherId, int studentId) {
        super(id, mark);
        this.teacherId = teacherId;
        this.studentId = studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getTeacherId() {
        return teacherId;
    }


}
