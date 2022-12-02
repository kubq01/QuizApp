package com.example.QuizApp.data.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    final UserRepository repo;

    final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }
    public List<User> showAll()
    {
        return repo.findAll();
    }

    public void insert(User user)
    {
        User encodedUser = encodePassword(user);
        repo.save(encodedUser);
    }

    public List<User> showByType(UserType type)
    {
        return repo.findByType(type.name());
    }

    private User encodePassword(User user){
        String password = user.getPassword();
        user.setPassword(encoder.encode(password));
        return user;
    }
}
