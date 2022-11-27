package com.example.QuizApp.data.exercises;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query("Select u from Exercise u where descriminator_column = :type")
    List<Exercise> findByType(@Param("type") String type);
}
