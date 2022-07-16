package com.techleads.app.repository;

import com.techleads.app.models.CollegeStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<CollegeStudent, Integer> {

    CollegeStudent findByEmailAddress(String email);
}
