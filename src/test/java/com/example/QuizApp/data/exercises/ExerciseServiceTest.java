package com.example.QuizApp.data.exercises;

import com.example.QuizApp.data.quizes.Quiz;
import com.example.QuizApp.data.quizes.TeacherQuiz;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {



    @Mock
    ExerciseRepository exerciseRepository;

    @Mock
    Quiz quiz;
    ExerciseService exerciseService;

    @BeforeEach
    void setUp()
    {
        exerciseService = new ExerciseService(exerciseRepository);
    }

    @Test
    void hidingCorrectAnswerTest()
    {
        Exercise exercise = new ABCDExercise("Question",
                quiz,"notCorrect","notCorrect",
                "Correct","notCorrect", (short) 2);

        when(exerciseRepository.findById(anyLong())).thenReturn(Optional.of(exercise));

        assertEquals((short) 2, ((ABCDExercise) exercise).getCorrectAnswer());

        exercise = exerciseService.getExerciseForStudent(1L);

        assertEquals((short) -1, ((ABCDExercise) exercise).getCorrectAnswer());
    }

}