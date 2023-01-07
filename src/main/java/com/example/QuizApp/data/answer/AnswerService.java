package com.example.QuizApp.data.answer;

import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.result.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Answer showByExerciseAndQuizResult(Exercise exercise, QuizResult quizResult)
    {
        Optional<Answer> ans = repo.findAllByExerciseAndQuizResult(exercise,quizResult);
        if(ans.isPresent())
            return ans.get();

        return null;
    }
}
