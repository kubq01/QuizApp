package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.WrittenExercise;
import com.example.QuizApp.data.users.Student;
import com.example.QuizApp.data.users.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TeacherQuiz")
@Data
@NoArgsConstructor
@DiscriminatorValue("TEACHER")
public class TeacherQuiz extends Quiz {

    /*
    True if tests mark counts to students evaluation by avg
    (used for printing students marks)
     */
    private boolean countsToAvg;

    @ManyToOne
    @JoinColumn(name = "id", insertable=false, updatable=false)
    private Teacher teacher;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public TeacherQuiz(int id, int mark, Student student, ABCDExercise abcdExercise, WrittenExercise writtenExercise, boolean countsToAvg, Teacher teacher) {
        super(id, mark, student, abcdExercise, writtenExercise);
        this.countsToAvg = countsToAvg;
        this.teacher = teacher;
    }
}
