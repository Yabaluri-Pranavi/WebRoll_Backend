package com.example.webroll.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.webroll.Entity.Student;
import com.example.webroll.Repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentControllerTest {

    @Test
    void addStudentStoresPasswordHashAndNeverSerializesIt() throws Exception {
        StudentRepository studentRepository = mock(StudentRepository.class);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));
        StudentController controller = new StudentController(studentRepository, passwordEncoder);
        ArgumentCaptor<Student> savedStudent = ArgumentCaptor.forClass(Student.class);

        StudentController.StudentResponse response = controller.addStudent(
                new StudentController.CreateStudentRequest("Student Name", "R-123", "securepass"));

        verify(studentRepository).save(savedStudent.capture());
        assertEquals("Student Name", response.name());
        assertEquals("R-123", response.rollNumber());
        assertTrue(passwordEncoder.matches("securepass", savedStudent.getValue().getPassword()));
        assertFalse(Arrays.stream(response.getClass().getRecordComponents())
                .anyMatch(component -> component.getName().equals("password")));
        assertFalse(new ObjectMapper().writeValueAsString(savedStudent.getValue()).contains("password"));
    }
}
