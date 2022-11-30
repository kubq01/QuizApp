package com.example.QuizApp.data.users;

import com.example.QuizApp.data.Class.Class;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Student")
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("STUDENT")
public class Student extends User {
    @ManyToOne
    @JoinColumn(name = "students_class_id")
    private Class studentsClass;
}
