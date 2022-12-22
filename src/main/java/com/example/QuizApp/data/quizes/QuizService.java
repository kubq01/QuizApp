package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.ExerciseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    QuizRepository repo;

    @Autowired
    public QuizService(QuizRepository repo) {
        this.repo = repo;

    }

    public List<Quiz> showAll()
    {
       return repo.findAll();
    }

    public void insert(Quiz quiz)
    {
       repo.save(quiz);
    }

    public List<Quiz> showByType(QuizType type)
    {
        return repo.findByType(type.name());
    }

    public Boolean deleteById(Long id) {
        repo.deleteById(id);
        return true;
    }

    public void deleteAll()
    {
        repo.deleteAll();
    }

    public List<Quiz> showWithoutExercises(int classID)
    {
            List<Quiz> list = repo.findByClassId(classID);

            for(Quiz quiz : list)
            {
                quiz.hideExercises();

                if(quiz instanceof TeacherQuiz)
                    ((TeacherQuiz) quiz).hideTeacher();
            }

            return list;
    }

    public Quiz showSafeByID(Long quizID)
    {
        Optional<Quiz> quiz = repo.findById(quizID);

        if(quiz.isPresent())
        {
            if(quiz.get() instanceof TeacherQuiz)
                ((TeacherQuiz) quiz.get()).hideTeacher();

            return quiz.get();
        }else
        {
            //TODO: throw error
            return null;
        }
    }
}
