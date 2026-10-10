package com.example.webroll.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.webroll.Entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}