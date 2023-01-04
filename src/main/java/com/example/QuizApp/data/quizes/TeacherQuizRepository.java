package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.users.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherQuizRepository extends JpaRepository<TeacherQuiz,Long> {
    public List<TeacherQuiz> findAllByTeacher(Teacher teacher);
}
