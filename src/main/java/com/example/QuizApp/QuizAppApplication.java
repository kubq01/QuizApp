package com.example.QuizApp;

import com.example.QuizApp.data.Class.Class;
import com.example.QuizApp.data.Class.ClassService;
import com.example.QuizApp.data.exercises.*;
import com.example.QuizApp.data.quizes.Quiz;
import com.example.QuizApp.data.quizes.QuizService;
import com.example.QuizApp.data.quizes.StudentQuiz;
import com.example.QuizApp.data.quizes.TeacherQuiz;
import com.example.QuizApp.data.users.Admin;
import com.example.QuizApp.data.users.Student;
import com.example.QuizApp.data.users.Teacher;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	CommandLineRunner run(UserService service, ExerciseService exerciseService, QuizService quizService, ClassService classService) {
		return args -> {
			service.deleteAll();
			quizService.deleteAll();
			exerciseService.deleteAll();
			classService.deleteAll();
			Class studentClass = new Class();
			classService.insert(studentClass);
			Class studentClass2 = new Class();
			classService.insert(studentClass2);
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
					"student",studentClass);
			service.insert(student);
			Exercise ex1 = new ABCDExercise("A",
					2,
					2,
					"A",
					"B",
					"C",
					"D",
					(short) 1,
					(short) 1);
			exerciseService.insert(ex1);
			Exercise ex2 = new WrittenExercise("AW",
					2,
					2,
					"Ans");
			exerciseService.insert(ex2);
			Set<Exercise> set = new HashSet<>();

			set.add(ex1);
			set.add(ex2);
			Quiz quiz = new TeacherQuiz(set,false,teacher,"sub",studentClass);
			quizService.insert(quiz);
			Quiz quiz2 = new TeacherQuiz(null,false,teacher,"sub",studentClass2);
			quizService.insert(quiz2);
		};
	}

}
