package com.example.QuizApp.data.Class;

import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.users.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    public List<Class> findAllByTeacher(Teacher teacher);

    public Class findByIdAndTeacher(Long id, Teacher teacher);
}
