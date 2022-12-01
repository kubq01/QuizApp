package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.users.Teacher;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class QuizBuilder {

    public StudentQuiz makeStudentQuiz(Set<Exercise> exercises)
    {
        return new StudentQuiz(exercises);
    }

    public TeacherQuiz makeTeacherQuiz(Set<Exercise> exercises, Boolean countsToAvg, Teacher teacher, String subject)
    {
        return new TeacherQuiz(exercises,countsToAvg,teacher,subject);
    }
}
