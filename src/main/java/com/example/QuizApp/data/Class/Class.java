
package com.example.QuizApp.data.Class;

import com.example.QuizApp.data.users.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    /*
    @OneToMany(mappedBy = "studentsClass", orphanRemoval = true)
    private Set<Student> students = new LinkedHashSet<>();

     */


}
