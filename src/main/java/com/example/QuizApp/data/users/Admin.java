package com.example.QuizApp.data.users;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Admin")
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    public Admin(int id, String firstName,
                 String LastName, String login, String password) {
        super(id, firstName, LastName, login, password);
    }
}
