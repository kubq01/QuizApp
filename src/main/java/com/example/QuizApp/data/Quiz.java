package com.example.QuizApp.data;

import javax.persistence.*;
import java.util.List;

@MappedSuperclass
public abstract class Quiz {

    @Id
    @SequenceGenerator(
            name = "quiz_sequence",
            sequenceName = "quiz_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "quiz_sequence"
    )
    int id;
    int mark;

    public Quiz() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public int getMark() {
        return mark;
    }

    public Quiz(int id, int mark) {
        this.id = id;
        this.mark = mark;
    }
}
