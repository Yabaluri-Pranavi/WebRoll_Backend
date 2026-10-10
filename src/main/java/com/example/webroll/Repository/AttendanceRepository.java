package com.example.webroll.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.webroll.Entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
}