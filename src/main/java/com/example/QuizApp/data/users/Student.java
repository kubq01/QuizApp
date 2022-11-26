package com.example.QuizApp.data.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Student")
@Data
@NoArgsConstructor
@DiscriminatorValue("STUDENT")
public class Student extends User {

    private String studentClass;    //TODO czy zrobić klase jako liste studentów???, ja bym to zostawil jako pole bo bedzie prosciej logike zapytan zrobic

    @ManyToOne
    @JoinColumn(name = "id", insertable=false, updatable=false)
    private Teacher teacher;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student(int id, String firstName, String LastName,
                   String login, String password,
                   String studentClass, Teacher teacher) {
        super(id, firstName, LastName, login, password);
        this.studentClass = studentClass;
        this.teacher = teacher;
    }
}
