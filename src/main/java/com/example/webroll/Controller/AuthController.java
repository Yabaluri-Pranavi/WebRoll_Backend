package com.example.webroll.Controller;

import com.example.webroll.Entity.Student;
import com.example.webroll.JwtUtil;
import com.example.webroll.Repository.StudentRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(
            StudentRepository studentRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        if (!"student".equalsIgnoreCase(request.role())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid username or password.");
        }

        Student student = studentRepository.findByRollNumber(request.username().trim());
        if (student == null
                || student.getPassword() == null
                || !passwordEncoder.matches(request.password(), student.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid username or password.");
        }

        return new LoginResponse(
                jwtUtil.generateToken(student.getRollNumber(), "Student"),
                student.getRollNumber(),
                "Student");
    }

    public record LoginRequest(
            @NotBlank String username,
            @NotBlank String password,
            @NotBlank String role) {
    }

    public record LoginResponse(String token, String username, String role) {
    }
}
