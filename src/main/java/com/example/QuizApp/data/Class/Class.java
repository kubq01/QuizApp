
package com.example.QuizApp.data.Class;

import com.example.QuizApp.data.users.Student;
import com.example.QuizApp.data.users.Teacher;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;



    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public Long getTeacherId()
    {
        return teacher.getId();
    }

    public String getTeacherName()
    {
        return (teacher.getFirstName() + " " + teacher.getLastName());
    }

    public Class(Teacher teacher) {
        this.teacher = teacher;
    }

    /*
    @OneToMany(mappedBy = "studentsClass", orphanRemoval = true)
    private Set<Student> students = new LinkedHashSet<>();

     */


}
