package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentQuizRepository extends JpaRepository<StudentQuiz,Long> {

    public List<StudentQuiz> findAllByStudent(Student student);
}
