package com.example.QuizApp.data.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("TEACHER")
public class Teacher extends User{

    public Teacher(Long id, String firstName, String LastName, String login, String password) {
        super(id, firstName, LastName, login, password);
    }

    public Teacher(String firstName, String lastName, String login, String password) {
        super(firstName, lastName, login, password);
    }

    @Override
    public String showRoleInPolish() {
        return "Nauczyciel";
    }
}
