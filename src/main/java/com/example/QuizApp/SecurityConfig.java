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
                        .antMatchers("/user/add")
                        .hasAuthority("ADMIN")
                        .antMatchers("/user/list")
                        .hasAuthority("ADMIN")
                        .antMatchers("/exercise/ID")
                        .hasAnyAuthority("STUDENT", "ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .httpBasic();
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