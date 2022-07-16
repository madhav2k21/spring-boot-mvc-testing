package com.techleads.app.service;

import com.techleads.app.models.CollegeStudent;
import com.techleads.app.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {
    @Autowired
    private StudentRepository studentRepository;

    public void createStudent(String fristName, String lastName, String email) {

        CollegeStudent collegeStudent = new CollegeStudent(fristName, lastName, email);
        collegeStudent.setId(0);
        studentRepository.save(collegeStudent);

    }

    public boolean checkIfStudentIsNull(Integer id) {

        Optional<CollegeStudent> studentById = studentRepository.findById(id);

        return studentById.isPresent() ? true : false;

    }
}
