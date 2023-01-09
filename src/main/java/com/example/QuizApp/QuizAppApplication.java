package com.example.QuizApp;

import com.example.QuizApp.data.Class.Class;
import com.example.QuizApp.data.Class.ClassService;
import com.example.QuizApp.data.Class.ClassToStudentRelation;
import com.example.QuizApp.data.exercises.ABCDExercise;
import com.example.QuizApp.data.exercises.Exercise;
import com.example.QuizApp.data.exercises.ExerciseService;
import com.example.QuizApp.data.quizes.Quiz;
import com.example.QuizApp.data.quizes.QuizService;
import com.example.QuizApp.data.quizes.StudentQuiz;
import com.example.QuizApp.data.quizes.TeacherQuiz;
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

import java.time.LocalDate;

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
	CommandLineRunner run(UserService service,
						  ExerciseService exerciseService,
						  QuizService quizService,
						  ClassService classService,
						  QuizResultService quizResultService) {
		return args -> {
			Teacher teacher = new Teacher(null,"Janusz",
					"Kowalski","Janusz","Kowal123");
			service.insert(teacher);
			Admin admin = new Admin(null, "Bartosz",
					"Walaszek",
					"admin",
					"admin");
			service.insert(admin);
			Student student = new Student(null, "Marcin",
					"Nowak",
					"student",
					"student");
			service.insert(student);
			Student student2 = new Student(null, "Grzegorz",
					"Niedziela",
					"g-niedziela",
					"haslo123");
			service.insert(student2);

			Class class1 = new Class(teacher);
			classService.insert(class1);

			ClassToStudentRelation relation = new ClassToStudentRelation(student,class1);
			classService.insertRel(relation);
			ClassToStudentRelation relation2 = new ClassToStudentRelation(student2,class1);
			classService.insertRel(relation2);

			Quiz quiz = new TeacherQuiz(false,
					LocalDate.now(),30,teacher,"Historia",class1);
			quizService.insert(quiz);

			Exercise exercise1 = new ABCDExercise("Podaj rok zakończenia I Wojny Światowej.", quiz,
					"1918","1992","1914","1920", (short) 1);
			Exercise exercise2 = new ABCDExercise("Kto odkrył Amerykę.",
					quiz,"Mikołaj Kopernik","Amerigo Vespucci",
					"Krzysztof Kolumb","Krzysztof Gonciarz", (short) 3);

			exerciseService.insert(exercise1);
			exerciseService.insert(exercise2);

			QuizResult result = new QuizResult(null, null, false,
					0, 2, true, (TeacherQuiz) quiz,student);
			quizResultService.insert(result);

			Quiz quiz1 = new StudentQuiz("Programowanie w języku Java.",student);
			quizService.insert(quiz1);

			Exercise exercise3 = new ABCDExercise("Operator inkrementacji",
					quiz1,"++","+-","inc()","+", (short) 1);
			Exercise exercise4 = new ABCDExercise("Mechanizm współdzielenia funkcjonalności między klasami nazwyamy.",
					quiz1,"polimorfizm","konstruktor","dziedziczenie","enkapsulacja", (short) 3);

			exerciseService.insert(exercise3);
			exerciseService.insert(exercise4);

		};
	}

}
