package com.example.QuizApp.data.answer;

import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.result.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {


    public Optional<Answer> findAllByExerciseAndQuizResult(Exercise exercise, QuizResult quizResult);
}
