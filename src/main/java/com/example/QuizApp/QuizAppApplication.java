package com.example.QuizApp;

import com.example.QuizApp.data.exercises.*;
import com.example.QuizApp.data.quizes.Quiz;
import com.example.QuizApp.data.quizes.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@AllArgsConstructor
public class QuizAppApplication {

	private QuizService quizService;
	private ExerciseService exerciseService;
	//private Quiz quiz1 = new StudentQuiz(0,3,new Student(),new ABCDExercise(),new WrittenExercise());
	//private Quiz quiz2 = new TeacherQuiz(1,2,new Student(),new ABCDExercise(),new WrittenExercise(),false,new Teacher());


	public static void main(String[] args) {
		SpringApplication.run(QuizAppApplication.class, args);
	}

	@GetMapping
	public List<Exercise> test()
	{
		Exercise ex1 = new ABCDExercise(1,"ABCD",2,false,"a","b","c","d", (short) 2, (short) 2);
		Exercise ex2 = new WrittenExercise(2,"written",6,false,"ans",3);
		exerciseService.insert(ex1);
		exerciseService.insert(ex2);
		return exerciseService.showByType(ExerciseType.WRITTEN);
		//return exerciseService.showAll();
	}

}
