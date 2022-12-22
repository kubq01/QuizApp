package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    @Query("Select u from QuizResult u where quiz_id = :ID")
    List<QuizResult> showByQuizID(@Param("ID") Long quizID);

    @Query("Select u from QuizResult u where quiz_id = :quizID and student_id = :studentID")
    QuizResult showByQuizIDAndStudentID(@Param("quizID") Long quizID,@Param("studentID") Long studentID);
}
