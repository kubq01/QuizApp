package com.example.QuizApp;

import com.example.QuizApp.data.Class.Class;
import com.example.QuizApp.data.Class.ClassService;
import com.example.QuizApp.data.Class.ClassToStudentRelation;
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
	CommandLineRunner run(UserService service, ExerciseService exerciseService, QuizService quizService, ClassService classService, QuizResultService quizResultService) {
		return args -> {
			Teacher teacher = new Teacher(null,"Janusz","Kowalski","login","password");
			service.insert(teacher);
			Admin admin = new Admin(null, "Bartosz",
					"Walaszek",
					"abcd",
					"efgh");
			service.insert(admin);
			Student student = new Student(null, "Bartosz",
					"Maciaszek",
					"student",
					"student");
			service.insert(student);
			Student student2 = new Student(null, "Bartosz",
					"Gagatek",
					"password",
					"login");
			service.insert(student2);

			Class class1 = new Class(teacher);
			classService.insert(class1);

			ClassToStudentRelation relation = new ClassToStudentRelation(student,class1);
			classService.insertRel(relation);
			ClassToStudentRelation relation2 = new ClassToStudentRelation(student2,class1);
			classService.insertRel(relation2);

			Quiz quiz = new TeacherQuiz(false, LocalDate.now(),30,teacher,"subject",class1);
			quizService.insert(quiz);

			Exercise exercise1 = new ABCDExercise("q1", quiz,"aa","b","c","d", (short) 1);
			Exercise exercise2 = new ABCDExercise("q2",  quiz,"aa","bb","c","dd", (short) 3);

			exerciseService.insert(exercise1);
			exerciseService.insert(exercise2);

			QuizResult result = new QuizResult(2, (TeacherQuiz) quiz,student);
			quizResultService.insert(result);
		};
	}

}
