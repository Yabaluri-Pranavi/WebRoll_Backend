
package com.example.webroll.Controller;

import com.example.webroll.Entity.Student;
import com.example.webroll.Repository.StudentRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173"
})
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        student.setStudentId(null);
        return studentRepository.save(student);
    }
}
