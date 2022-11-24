package com.example.QuizApp.data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Teacher extends User{
    public Teacher() {
    }

    public Teacher(String firstName, String lastName, String login, String password) {
        super(firstName, lastName, login, password);
    }

}
