package com.example.QuizApp;

import com.example.QuizApp.data.Class.ClassService;
import com.example.QuizApp.data.exercises.*;
import com.example.QuizApp.data.quizes.*;
import com.example.QuizApp.data.quizes.enums.QuizStatus;
import com.example.QuizApp.data.result.QuizResult;
import com.example.QuizApp.data.result.QuizResultService;
import com.example.QuizApp.data.users.Admin;
import com.example.QuizApp.data.users.Student;
import com.example.QuizApp.data.users.Teacher;
import com.example.QuizApp.data.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@AllArgsConstructor
public class QuizAppApplication {

	/*private QuizService quizService;
	private ExerciseService exerciseService;*/

	public static void main(String[] args) {
		SpringApplication.run(QuizAppApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService service, ExerciseService exerciseService, QuizService quizService, ClassService classService, QuizResultService quizResultService) {
		return args -> {
			Teacher teacher = new Teacher("tea","cher","login","password");
			service.insert(teacher);
			Admin admin = new Admin(null, "Bartosz",
					"Walaszek",
					"abcd",
					"efgh");
			service.insert(admin);
			Student student = new Student("Bartosz",
					"Walaszek",
					"student",
					"student");
			service.insert(student);
			Student student2 = new Student("Bartosz nr2",
					"Walaszek nr2",
					"password",
					"login");
			service.insert(student2);

			Quiz quiz = new TeacherQuiz(false,teacher,"sub",null);
			quizService.insert(quiz);
			Quiz quiz2 = new TeacherQuiz(false,teacher,"sub",null);
			quizService.insert(quiz2);

			Exercise ex1 = new ABCDExercise(quiz,
					"A",
					2,
					2,
					"A",
					"B",
					"C",
					"D",
					(short) 1,
					(short) 1);
			exerciseService.insert(ex1);
			Exercise ex2 = new WrittenExercise(quiz,
					"AW",
					2,
					2,
					"Ans");
			exerciseService.insert(ex2);

			QuizResult quizResult = new QuizResult(4.5F, QuizStatus.SUBMITTED,30,quiz,student);
			quizResultService.insert(quizResult);
			QuizResult quizResult2 = new QuizResult(5.5F,QuizStatus.GRADED,10,quiz2,student);
			quizResultService.insert(quizResult2);
			QuizResult quizResult3 = new QuizResult(2.5F,QuizStatus.GRADED,150,quiz2,student2);
			quizResultService.insert(quizResult3);

			Class class1 = new Class(teacher);
			classService.insert(class1);

		};
	}

}
