package com.example.webroll.Controller;

import com.example.webroll.Entity.Student;
import com.example.webroll.JwtUtil;
import com.example.webroll.Repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Test
    void loginAuthenticatesStudentByRollNumberAndPassword() {
        StudentRepository studentRepository = mock(StudentRepository.class);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Student student = new Student();
        student.setRollNumber("R-123");
        student.setPassword(passwordEncoder.encode("securepass"));
        when(studentRepository.findByRollNumber("R-123")).thenReturn(student);
        JwtUtil jwtUtil = new JwtUtil("a-secure-test-secret-that-is-long-enough");
        AuthController controller = new AuthController(studentRepository, passwordEncoder, jwtUtil);

        AuthController.LoginResponse response = controller.login(
                new AuthController.LoginRequest(" R-123 ", "securepass", "Student"));

        assertEquals("R-123", response.username());
        assertEquals("Student", response.role());
        assertEquals("Student", jwtUtil.getRoleFromToken(response.token()));
    }

    @Test
    void loginRejectsIncorrectPassword() {
        StudentRepository studentRepository = mock(StudentRepository.class);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Student student = new Student();
        student.setRollNumber("R-123");
        student.setPassword(passwordEncoder.encode("securepass"));
        when(studentRepository.findByRollNumber("R-123")).thenReturn(student);
        AuthController controller = new AuthController(
                studentRepository,
                passwordEncoder,
                new JwtUtil("a-secure-test-secret-that-is-long-enough"));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> controller.login(
                        new AuthController.LoginRequest("R-123", "incorrect", "Student")));

        assertEquals(401, exception.getStatusCode().value());
    }
}
