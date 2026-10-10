package com.example.webroll.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.webroll.Entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}