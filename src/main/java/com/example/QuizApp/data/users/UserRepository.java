package com.example.QuizApp.data.users;

import com.example.QuizApp.data.quizes.Quiz;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("Select u from User u where descriminator_column = :type")
    List<User> findByType(@Param("type") String type);

    @Query("select u from User u where u.login = ?1")
    Optional<User> findByLogin(String login);

    List<User> findAllByActive(boolean active);

}
