package com.example.QuizApp.data.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    AnswerRepository repo;

    @Autowired
    AnswerService(AnswerRepository answerRepository)
    {
        repo = answerRepository;
    }

    public void insert(Answer answer)
    {
        repo.save(answer);
    }
}
