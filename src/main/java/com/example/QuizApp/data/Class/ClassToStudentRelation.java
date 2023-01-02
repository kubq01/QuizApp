package com.example.QuizApp.data.Class;

import com.example.QuizApp.data.users.Student;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassToStudentRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "connected_class_id")
    private Class connectedClass;

    public Class getConnectedClass() {
        return connectedClass;
    }

    public void setConnectedClass(Class connectedClass) {
        this.connectedClass = connectedClass;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
