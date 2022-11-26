package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.WrittenExercise;
import com.example.QuizApp.data.users.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="descriminatorColumn")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Quiz {        //TODO metoda zapisu odpowiedzi,
    //TODO zapis bedzie zadeklarowny w exercise, a kazdy quiz bedzie sie skladal z kilku exercise
    //TODO chodzilo mi tam o pojedyncze zadanie na tescie ale uznalem ze lepije to przetlumaczyc w ten sposob niz jako task


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int mark;
    @ManyToOne
    @JoinColumn(name = "id", insertable=false, updatable=false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "id", insertable=false, updatable=false)
    private ABCDExercise abcdExercise;

    @ManyToOne
    @JoinColumn(name = "id", insertable=false, updatable=false)
    private WrittenExercise writtenExercise;

}
