package com.lifestyle.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lifestyle.dto.LoginRequest;
import com.lifestyle.dto.RegisterRequest;
import com.lifestyle.entity.User;
import com.lifestyle.repository.UserRepository;
import com.lifestyle.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    
    public String register(RegisterRequest req) {

        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            return "Email already exists";
        }

        User user = new User();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setEmail(req.getEmail());
        user.setMobile(req.getMobile());
        user.setPassword(encoder.encode(req.getPassword()));

        userRepository.save(user);

        return "Registered Successfully";
    }


    public String login(LoginRequest req) {

        return userRepository.findByEmail(req.getEmail())
                .filter(user -> encoder.matches(req.getPassword(), user.getPassword()))
                .map(user -> jwtUtil.generateToken(user.getEmail()))
                .orElse(null);
    }
}
