
package com.example.QuizApp.data.Class;

import com.example.QuizApp.data.users.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Class", indexes = {
        @Index(name = "idx_class_teacher_id", columnList = "teacher_id")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    //@GenericGenerator(name = "uuid", strategy = "uuid2")

    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne(optional = true)
    @JoinColumn(name = "teacher_id", nullable = true)
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
