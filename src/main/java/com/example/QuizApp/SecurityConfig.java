package com.example.QuizApp;

import com.example.QuizApp.data.users.DBAuthenticationProvider;
import com.example.QuizApp.data.users.DBUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorization -> authorization
                        //user
                        .antMatchers("/user/listClasses")
                        .hasAuthority("TEACHER")
                        .antMatchers("/user/listStudents")
                        .hasAuthority("TEACHER")
                        .antMatchers("/user/studentInfoTeacher")
                        .hasAuthority("TEACHER")
                        .antMatchers("/user/aIndex/*")
                        .hasAuthority("ADMIN")
                        .antMatchers("/user/aIndex/*/*")
                        .hasAuthority("ADMIN")
                        .antMatchers("/user/aIndex/*/*/*")
                        .hasAuthority("ADMIN")
                        .antMatchers("/user/joinClass/*")
                        .hasAuthority("STUDENT")
                        .antMatchers("/user/joinClass")
                        .hasAuthority("STUDENT")
                        //Quiz
                        .antMatchers("/quiz/listByT")
                        .hasAuthority("TEACHER")
                        .antMatchers("/quiz/addTeacherQuiz")
                        .hasAuthority("TEACHER")
                        .antMatchers("/quiz/addTeacherQuiz/*")
                        .hasAuthority("TEACHER")
                        .antMatchers("/quiz/viewQuizT")
                        .hasAuthority("TEACHER")
                        .antMatchers("/quiz/addABCD")
                        .hasAnyAuthority("STUDENT", "TEACHER")
                        .antMatchers("/quiz/addABCD/*")
                        .hasAnyAuthority("STUDENT", "TEACHER")
                        .antMatchers("/quiz/listByS")
                        .hasAuthority("STUDENT")
                        .antMatchers("/quiz/addStudentQuiz")
                        .hasAuthority("STUDENT")
                        .antMatchers("/quiz/addStudentQuiz/*")
                        .hasAuthority("STUDENT")
                        .antMatchers("/quiz/listForStudent")
                        .hasAuthority("STUDENT")
                        .antMatchers("/quiz/startQuiz")
                        .hasAuthority("STUDENT")
                        .antMatchers("/quiz/solveABCD")
                        .hasAuthority("STUDENT")
                        .antMatchers("/quiz/sendABCD")
                        .hasAuthority("STUDENT")
                        .antMatchers("/quiz/finishQuiz")
                        .hasAuthority("STUDENT")
                        .antMatchers("/quiz/startStudentQuiz")
                        .hasAuthority("STUDENT")
                        .antMatchers("/quiz/solveStudentABCD")
                        .hasAuthority("STUDENT")
                        .antMatchers("/quiz/sendStudentABCD")
                        .hasAuthority("STUDENT")
                        .antMatchers("/quiz/finishStudentQuiz")
                        .hasAuthority("STUDENT")
                        .antMatchers("/quiz/viewQuiz")
                        .hasAuthority("STUDENT")

                        //others
                        .antMatchers("/bootstrap/**",
                        "/css/**",
                        "/img/**",
                        "/js/**").permitAll()

                        .anyRequest()
                        .authenticated()

                )
                .formLogin(formLogin -> formLogin
                       .loginPage("/login")
                        //.loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/loginError")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                );
                //.httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new DBUserDetailsServiceImpl();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(new DBAuthenticationProvider());
        return authenticationManagerBuilder.build();
    }
}