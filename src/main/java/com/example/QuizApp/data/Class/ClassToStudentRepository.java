package com.example.QuizApp.data.Class;

import com.example.QuizApp.data.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassToStudentRepository extends JpaRepository<ClassToStudentRelation, Long> {
    public List<ClassToStudentRelation> findAllByMyClass(Class myClass);

    public List<ClassToStudentRelation> findAllByStudent(Student student);

    public void deleteAllByMyClass(Class myClass);
}
