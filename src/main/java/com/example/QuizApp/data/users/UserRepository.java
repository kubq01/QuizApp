package com.example.QuizApp.data.users;

import com.example.QuizApp.data.quizes.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("Select u from User u where descriminator_column = :type")
    List<User> findByType(@Param("type") String type);
}
