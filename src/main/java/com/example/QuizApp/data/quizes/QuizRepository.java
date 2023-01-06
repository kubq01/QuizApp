package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.quizes.Quiz;
import com.example.QuizApp.data.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {

    @Query("Select u from Quiz u where descriminator_column = :type")
    List<Quiz> findByType(@Param("type") String type);

    @Query("Select u from Quiz u where descriminator_column = TeacherQuiz and students_class_id = :ID")
    List<Quiz> findByClassId(@Param("ID") int ID);
}

