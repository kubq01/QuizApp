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
			Teacher teacher2 = new Teacher(null,"Mariusz",
					"Kowal","Mariusz","Mar111");
			service.insert(teacher2);
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

			Class class2 = new Class(teacher2);
			classService.insert(class2);

			Class class3 = new Class(teacher);
			classService.insert(class3);

			ClassToStudentRelation relation = new ClassToStudentRelation(student,class1);
			classService.insertRel(relation);
			ClassToStudentRelation relation2 = new ClassToStudentRelation(student2,class1);
			classService.insertRel(relation2);

			Quiz quiz = new TeacherQuiz(true,
					LocalDate.now(),2,teacher,"Historia",class1);
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


			Quiz quizMath = new TeacherQuiz(true,
					LocalDate.now(),15,teacher,"Matematyka",class1);
			quizService.insert(quizMath);

			Exercise exercise5 = new ABCDExercise("10+10=", quizMath,
					"1","2","0","20", (short) 4);
			Exercise exercise6 = new ABCDExercise("Wzór na pole trójkąta",
					quizMath,"a*h/2","a+b*h",
					"(a+b)*h","10", (short) 1);
			Exercise exercise7 = new ABCDExercise("100*100", quizMath,
					"10","10000","20","1", (short) 2);
			Exercise exercise8 = new ABCDExercise("Wzór na pole kwadratu",
					quizMath,"a^3","a*h",
					"a*a","a*b", (short) 3);
			Exercise exercise9 = new ABCDExercise("2+5*10", quizMath,
					"70","52","2510","0", (short) 2);
			Exercise exercise10 = new ABCDExercise("25/0",
					quizMath,"Nie można dzielić przez zero","5",
					"25","0", (short) 1);
			Exercise exercise11 = new ABCDExercise("Wzór na deltę", quizMath,
					"x+y","a+b+c","b^2-4ac","a^2-b+c", (short) 3);
			Exercise exercise12 = new ABCDExercise("Kto wymyślił twierdzenie Pitagorasa",
					quizMath,"Mikołaj Kopernik","Homer",
					"Pitagoras","Tales", (short) 3);
			Exercise exercise13 = new ABCDExercise("Jakie jest przybliżenie liczby pi do 4 cyfry po przecinku", quizMath,
					"3.1415","3.14","2.7813","3.1411", (short) 1);
			Exercise exercise14 = new ABCDExercise("Ile wynosi pierwiastek z 2",
					quizMath,"4","2",
					"1.4142","1.222", (short) 3);

			exerciseService.insert(exercise5);
			exerciseService.insert(exercise6);
			exerciseService.insert(exercise7);
			exerciseService.insert(exercise8);
			exerciseService.insert(exercise9);
			exerciseService.insert(exercise10);
			exerciseService.insert(exercise11);
			exerciseService.insert(exercise12);
			exerciseService.insert(exercise13);
			exerciseService.insert(exercise14);


			QuizResult result3 = new QuizResult(null, null, false,
					0, 2, true, (TeacherQuiz) quizMath,student);
			quizResultService.insert(result3);

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
