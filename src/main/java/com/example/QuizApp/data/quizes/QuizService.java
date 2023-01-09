package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.quizes.enums.QuizType;
import com.example.QuizApp.data.users.Student;
import com.example.QuizApp.data.users.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;


@Service
public class QuizService {

    private final QuizRepository repo;
    private final TeacherQuizRepository repoT;

    private final StudentQuizRepository repoS;

    @Autowired
    public QuizService(QuizRepository repo, TeacherQuizRepository repoT, StudentQuizRepository repoS) {
        this.repo = repo;
        this.repoT = repoT;
        this.repoS = repoS;
    }


    public List<Quiz> showAll()
    {
       return repo.findAll();
    }

    public List<TeacherQuiz> showAllByTeacher(Teacher teacher)
    {
        return repoT.findAllByTeacher(teacher);
    }

    public Quiz insert(Quiz quiz) {
       return repo.save(quiz);
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

    public List<Quiz> showByClass(int classID)
    {
         return repo.findByClassId(classID);
    }

    public Quiz showSafeByID(Long quizID)
    {
        Optional<Quiz> quiz = repo.findById(quizID);

        if(quiz.isPresent())
        {
           // if(quiz.get() instanceof TeacherQuiz)
             //   ((TeacherQuiz) quiz.get()).hideTeacher();

            return quiz.get();
        }else
        {
            //TODO: throw error
            return null;
        }

    }

    public List<StudentQuiz> showAllByStudent(Student currentStudent) {
        return repoS.findAllByStudent(currentStudent);
    }
}
