package com.taskmanager.taskmanager_api.security;

import com.taskmanager.taskmanager_api.model.User;
import com.taskmanager.taskmanager_api.repository.UserRepository;
import com.taskmanager.taskmanager_api.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //This method tells Spring Security:
    //When the user tries to Log in, find the user by email in our database.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found with email: "+email));

        // Convert your User into a Spring Security User
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}

//
//Spring Security does NOT know your database.
//
//It doesn’t know:
//    how to find users
//    how passwords are stored
//    how roles are stored