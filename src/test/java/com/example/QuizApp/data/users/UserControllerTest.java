package com.example.QuizApp.data.users;

import com.example.QuizApp.data.Class.ClassService;
import com.example.QuizApp.data.exercises.ExerciseService;
import com.example.QuizApp.data.quizes.QuizService;
import com.example.QuizApp.data.quizes.TeacherQuiz;
import com.example.QuizApp.data.result.QuizResult;
import com.example.QuizApp.data.result.QuizResultService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private ClassService classService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private QuizResultService quizResultService;
    @MockBean
    private ExerciseService exerciseService;
    @MockBean
    private QuizService quizService;

    @Test
    @WithMockUser(username = "test", password = "test", roles = "TEACHER")
    void studentInfoTeacherTest() throws Exception {

        User user = new Student( "Marcin",
                "Nowak",
                "student",
                "student");
        QuizResult result1 = new QuizResult((Long) null, (float) 100, false,
                0, 2, true, null,null);
        QuizResult result2 = new QuizResult((Long) null, (float) 3, true,
                0, 2, true, null,null);
        QuizResult result3 = new QuizResult((Long) null, (float) 1, true,
                0, 2, true, null,null);
        QuizResult result4 = new QuizResult((Long) null, (float) 100, true,
                0, 2, false, null,null);

        List<QuizResult> quizResults  = new ArrayList<>();
        quizResults.add(result1);
        quizResults.add(result2);
        quizResults.add(result3);
        quizResults.add(result4);

        when(quizResultService.getResultsByStudent(any(Student.class))).thenReturn(quizResults);

        when(userService.showByID(anyLong())).thenReturn(user);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/studentInfoTeacher")
                        .param("ID", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("mean",2.0F))
                .andExpect(MockMvcResultMatchers.model().attribute("numberOfQuizes",3))
                .andExpect(MockMvcResultMatchers.model().attribute("student",user));
    }

}