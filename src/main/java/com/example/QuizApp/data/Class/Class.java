/* TODO to na razie trzeba rozplanować pod względem bazy.
package com.example.QuizApp.data.Class;

import com.example.QuizApp.data.users.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "studentsClass", orphanRemoval = true)
    private Set<Student> students = new LinkedHashSet<>();
}
*/
