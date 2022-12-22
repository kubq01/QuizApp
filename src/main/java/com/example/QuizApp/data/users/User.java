package com.example.QuizApp.data.users;


import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.WrittenExercise;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import javax.persistence.*;

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
    private String firstName;
    private String LastName;
    private String login;
    private String password;

    public User(String firstName, String lastName, String login, String password) {
        this.firstName = firstName;
        LastName = lastName;
        this.login = login;
        this.password = password;
    }
}
