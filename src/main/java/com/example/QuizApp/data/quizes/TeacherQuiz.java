package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.users.Student;
import com.example.QuizApp.data.users.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TeacherQuiz")
@Data
@NoArgsConstructor
public class TeacherQuiz extends Quiz {

    /*
    True if tests mark counts to students evaluation by avg
    (used for printing students marks)
     */
    private boolean countsToAvg;

    @ManyToOne
    @JoinColumn(name = "id")
    private Teacher teacher;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public TeacherQuiz(int id, int mark, Student student, boolean countsToAvg, Teacher teacher) {
        super(id, mark, student);
        this.countsToAvg = countsToAvg;
        this.teacher = teacher;
    }
}
