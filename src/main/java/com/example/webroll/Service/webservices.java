package com.example.webroll.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webroll.Repository.AttendanceRepository;
import com.example.webroll.Repository.StudentRepository;
import com.example.webroll.Repository.SubjectRepository;
import com.example.webroll.Repository.TeacherRepository;

@Service
public class webservices {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

}