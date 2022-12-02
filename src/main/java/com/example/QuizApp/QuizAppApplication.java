package com.example.QuizApp;

import com.example.QuizApp.data.exercises.*;
import com.example.QuizApp.data.quizes.Quiz;
import com.example.QuizApp.data.quizes.QuizService;
import com.example.QuizApp.data.quizes.StudentQuiz;
import com.example.QuizApp.data.users.Admin;
import com.example.QuizApp.data.users.Student;
import com.example.QuizApp.data.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@SpringBootApplication
@RestController
@AllArgsConstructor
public class QuizAppApplication {

	/*private QuizService quizService;
	private ExerciseService exerciseService;*/

	public static void main(String[] args) {
		SpringApplication.run(QuizAppApplication.class, args);
	}

	/*@GetMapping
	public List<Exercise> test()
	{
		Exercise ex1 = new ABCDExercise(1,"ABCD",2,false,"a","b","c","d", (short) 2, (short) 2);
		Exercise ex2 = new WrittenExercise(2,"written",6,false,"ans",3);
		exerciseService.insert(ex1);
		exerciseService.insert(ex2);
		return exerciseService.showByType(ExerciseType.WRITTEN);
	}*/

	@Bean
	CommandLineRunner run(UserService service) {
		return args -> {
			Admin admin = new Admin(null, "Bartosz", "Walaszek", "abcd", "efgh");
			service.insert(admin);
			/*Exercise ex1 = new ABCDExercise(1L,"ABCD",2,
					false,"a","b","c","d", (short) 2, (short) 2);
			Exercise ex2 = new WrittenExercise(2L,"written",
					6,false,"ans",3);
			exerciseService.insert(ex1);
			exerciseService.insert(ex2);*/
		};
	}

}
