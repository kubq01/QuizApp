package com.example.QuizApp.data.Class;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {

    private ClassRepository repo;

    @Autowired
    public ClassService(ClassRepository repo)
    {
        this.repo = repo;
    }

    public void insert(Class studentClass)
    {
        repo.save(studentClass);
    }

    public void deleteAll()
    {
        repo.deleteAll();
    }

    public List<Class> showAll()
    {
        return repo.findAll();
    }
}
