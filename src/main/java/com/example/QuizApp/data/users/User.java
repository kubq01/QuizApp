package com.example.QuizApp.data.users;


import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.WrittenExercise;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="descriminatorColumn")
@Table(name = "usr")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "userType")
@JsonSubTypes({
        @JsonSubTypes.Type(value= Admin.class, name = "Admin"),
        @JsonSubTypes.Type(value= Teacher.class, name = "Teacher"),
        @JsonSubTypes.Type(value= Student.class, name = "Student")})
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "Imie nie może być puste.")
    @Size(min = 2, max = 25, message = "Imie może zawierać od 2 do 25 znaków.")
    private String firstName;
    @NotEmpty(message = "Nazwisko nie może być puste.")
    @Size(min = 2, max = 35, message = "Nazwisko może zawierać od 2 do 35 znaków.")
    private String lastName;

    @NotEmpty(message = "Login nie może być pusty.")
    @Size(min = 2, max = 50, message = "Login może zawierać od 2 do 50 znaków.")
    @Column(name = "login", unique = true)
    private String login;

    @NotEmpty(message = "Hasło nie może być puste.")
    //@Size(min = 2, max = 30, message = "Hasło może zawierać od 6 do 20 znaków.")    //TODO zamienić na 6 przy działającej aplikacji
    private String password;

    public User(String firstName, String lastName, String login, String password) {
        this.firstName = firstName;
        lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public abstract String showRoleInPolish();
}
