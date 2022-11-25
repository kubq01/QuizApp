package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.users.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Quiz {        //TODO metoda zapisu odpowiedzi

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int mark;
    @ManyToOne
    @JoinColumn(name = "id")
    private Student student;

}
