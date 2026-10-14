
package com.example.webroll.Controller;

import com.example.webroll.Entity.Student;
import com.example.webroll.Repository.StudentRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentController(
            StudentRepository studentRepository,
            PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<StudentResponse> getStudents() {
        return studentRepository.findAll().stream()
                .map(StudentResponse::from)
                .toList();
    }

    @PostMapping
    public StudentResponse addStudent(
            @Valid @RequestBody CreateStudentRequest request) {
        if (request.password().getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password must be at most 72 UTF-8 bytes.");
        }

        Student student = new Student();
        student.setStudentId(null);
        student.setName(request.name().trim());
        student.setRollNumber(request.rollNumber().trim());
        student.setPassword(passwordEncoder.encode(request.password()));
        return StudentResponse.from(studentRepository.save(student));
    }

    public record CreateStudentRequest(
            @NotBlank String name,
            @NotBlank String rollNumber,
            @NotBlank @Size(min = 8, max = 72) String password) {
    }

    public record StudentResponse(Integer studentId, String name, String rollNumber) {
        private static StudentResponse from(Student student) {
            return new StudentResponse(
                    student.getStudentId(),
                    student.getName(),
                    student.getRollNumber());
        }
    }
}
