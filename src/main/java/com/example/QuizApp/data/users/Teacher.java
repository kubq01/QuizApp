package com.example.QuizApp.data.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Teacher")
@Data
@NoArgsConstructor
@DiscriminatorValue("TEACHER")
public class Teacher extends User{

    public Teacher(int id, String firstName, String LastName, String login, String password) {
        super(id, firstName, LastName, login, password);
    }
}
