package com.example.QuizApp.data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Student extends User{

    String studentClass;
    int teacherId;

    public Student() {
    }

    public Student(String firstName, String lastName, String login, String password, String studentClass, int teacherId) {
        super(firstName, lastName, login, password);
        this.studentClass = studentClass;
        this.teacherId = teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentClass() {
        return studentClass;
    }
}
