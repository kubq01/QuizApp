package com.example.QuizApp.data.result;

import com.example.QuizApp.data.users.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizResultService {

    private QuizResultRepository repo;

    @Autowired
    public QuizResultService(QuizResultRepository repo)
    {
        this.repo = repo;
    }

    public void insert(QuizResult quizResult)
    {
        repo.save(quizResult);
    }

    public List<QuizResult> getByQuizID(Long quizID)
    {
        return repo.showByQuizID(quizID);
    }

    public QuizResult getByQuizIDAndStudentID(Long quizID, Long studentID)
    {
        return repo.showByQuizIDAndStudentID(quizID, studentID);
    }

    public List<QuizResult> getAll()
    {
        return repo.findAll();
    }

    public void deleteAll()
    {
        repo.deleteAll();
    }

    public List<QuizResult> getResultsByStudent(Student currentStudent) {
        return repo.findAllByStudent(currentStudent);
    }
}
