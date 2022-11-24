package com.example.QuizApp.data;

import com.example.QuizApp.data.User;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Admin extends User {
    public Admin() {
    }

    public Admin(String firstName, String lastName, String login, String password) {
        super(firstName, lastName, login, password);
    }

}
