package com.example.QuizApp.data.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Admin")
@Data
@NoArgsConstructor
public class Admin extends User {

    public Admin(int id, String firstName,
                 String LastName, String login, String password) {
        super(id, firstName, LastName, login, password);
    }
}
