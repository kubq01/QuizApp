package com.example.QuizApp.data.users;

import com.example.QuizApp.data.Class.ClassRepository;
import com.example.QuizApp.data.quizes.TeacherQuizRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    static
    UserRepository userRepository;
    @Mock
    static
    ClassRepository classRepository;
    @Mock
    static
    TeacherQuizRepository quizRepository;


    static UserService userService;

    @BeforeAll
    static void setUp()
    {
        userService = new UserService(userRepository,new BCryptPasswordEncoder(),classRepository,quizRepository);
    }


    @Test
    void testPasswordEncoder()
    {
        User user1 = new Student( "Student",
                "S1",
                "login1",
                "password1");

        User user2 = new Student( "Student",
                "S2",
                "login2",
                "password2");

        assertNotNull(user1);
        assertNotNull(user2);


        String passwordPlain = user1.getPassword();
        String passwordEncryptedUser1 = userService.encodePassword(user1).getPassword();
        String passwordEncryptedUser2 = userService.encodePassword(user2).getPassword();


        assertAll(
                () -> assertNotEquals(passwordPlain,passwordEncryptedUser1),
                () -> assertNotEquals(passwordEncryptedUser1,passwordEncryptedUser2)
                );
    }

}