package com.example.webroll.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.webroll.Entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}