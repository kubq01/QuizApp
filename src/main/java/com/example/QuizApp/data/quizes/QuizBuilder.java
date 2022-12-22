package com.example.QuizApp.data.quizes;

import com.example.QuizApp.data.Class.Class;
import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.ExerciseType;
import com.example.QuizApp.data.users.Teacher;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@NoArgsConstructor
public class QuizBuilder {
    private Set<Exercise> exercises;
    private Boolean countsToAvg;
    private LocalDateTime start;
    private LocalDateTime end;
    private Integer quizTimeInMinutes;
    private Teacher teacher;
    private String subject;

    public QuizBuilder setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
        return this;
    }


    public QuizBuilder setCountsToAvg(Boolean countsToAvg) {
        this.countsToAvg = countsToAvg;
        return this;

    }

    public QuizBuilder setStart(LocalDateTime start) {
        this.start = start;
        return this;
    }

    public QuizBuilder setEnd(LocalDateTime end) {
        this.end = end;
        return this;
    }

    public QuizBuilder setQuizTimeInMinutes(Integer quizTimeInMinutes) {
        this.quizTimeInMinutes = quizTimeInMinutes;
        return this;
    }

    public QuizBuilder setTeacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public QuizBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Quiz build(QuizType type) {
        switch (type) {
            case TEACHER:
                return new TeacherQuiz(exercises, countsToAvg,
                        start, end, quizTimeInMinutes, teacher, subject);
            case STUDENT:
                return new StudentQuiz(exercises);
            default:
                return null; //throw exception?
        }
    }

}
