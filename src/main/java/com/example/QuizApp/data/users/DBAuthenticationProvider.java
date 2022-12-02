package com.example.QuizApp.data.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DBAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private DBUserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String givenUsername = authentication.getName();
        String givenPassword = authentication.getCredentials().toString();
        DBUserDetails userDetails = (DBUserDetails) userDetailsService.loadUserByUsername(givenUsername);
        String username = userDetails.getUsername();
        String password = userDetails.getPassword();
        if (passwordEncoder.matches(givenPassword, password)){
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
            token.setDetails(userDetails.getUser());
            return token;
        }
        else {
            throw new BadCredentialsException("Invalid login details");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
