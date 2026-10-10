
package com.example.webroll.Controller;

import com.example.webroll.Entity.Attendance;
import com.example.webroll.Repository.AttendanceRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;

    public AttendanceController(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @GetMapping
    public List<Attendance> getAttendance() {
        return attendanceRepository.findAll();
    }

    @PostMapping
    public Attendance markAttendance(@RequestBody Attendance attendance) {
        attendance.setAttendanceId(null);
        return attendanceRepository.save(attendance);
    }
}
