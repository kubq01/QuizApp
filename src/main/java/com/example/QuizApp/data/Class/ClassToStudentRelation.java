package com.example.QuizApp.data.Class;

import com.example.QuizApp.data.users.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne
    @JoinColumn(name = "student_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;


    @ManyToOne
    @JoinColumn(name = "my_class_id")
    private Class myClass;

    public ClassToStudentRelation(Student student, Class myClass) {
        this.student = student;
        this.myClass = myClass;
    }
}
