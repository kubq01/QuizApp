package com.example.QuizApp.data.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Student")
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("STUDENT")
public class Student extends User {
    public Student(Long id, String firstName, String LastName, String login, String password) {
        super(id, firstName, LastName, login, password);
    }

    public Student(String firstName, String lastName, String login, String password) {
        super(firstName, lastName, login, password);
    }

    @Override
    public String showRoleInPolish() {
        return "Uczeń";
    }
}
