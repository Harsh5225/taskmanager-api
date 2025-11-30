package com.taskmanager.taskmanager_api.config;

import com.taskmanager.taskmanager_api.security.CustomUserDetailsService;
import com.taskmanager.taskmanager_api.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                // tell security how to load users
                // Connects your database with Spring Security login system.
                .userDetailsService(userDetailsService)




//    Spring Security works with a chain of filters.
//    We are telling it:
//        “Before checking username/password, first validate JWT token.”
//    This enables stateless authentication.
                //adding jwt filter before username password filter
                .addFilterBefore(jwtAuthenticationFilter,
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);




        return http.build();
    }



//This is used when a user tries to log in.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return  config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // encrypt passwords
    }
}
