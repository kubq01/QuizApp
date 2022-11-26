package com.example.QuizApp.data.users;

import com.example.QuizApp.data.quizes.Quiz;
import com.example.QuizApp.data.quizes.QuizRepository;
import com.example.QuizApp.data.quizes.QuizType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    public UserService(UserRepository repo) {
        this.repo = repo;

    }
    UserRepository repo;

    public List<User> showAll()
    {
        return repo.findAll();
    }

    public void insert(User user)
    {
        repo.save(user);
    }

    public List<User> showByType(UserType type)
    {
        return repo.findByType(type.name());
    }
}
