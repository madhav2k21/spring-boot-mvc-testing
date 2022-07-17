package com.techleads.app;

import com.techleads.app.models.CollegeStudent;
import com.techleads.app.repository.StudentRepository;
import com.techleads.app.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class StudentAndGradeServiceTest {

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("insert into student(id, firstname, lastname, email_address) values(1, 'madhav', 'anupoju', 'madhav@techm.com')");
    }

    @AfterEach
    void setupAfterEach() {
        jdbcTemplate.execute("delete from student");
    }

    @Test
    void createStudentService() {
        studentService.createStudent("madhav", "anupoju", "madhav@techm.com");
        CollegeStudent studnent = studentRepository.findByEmailAddress("madhav@techm.com");
        assertEquals("madhav@techm.com", studnent.getEmailAddress());
        assertEquals("madhav", studnent.getFirstname());
        assertEquals("anupoju", studnent.getLastname());
        assertEquals("madhav@techm.com",studnent.getEmailAddress());

    }

    @Test
    void isStudentNullCheck() {
        assertTrue(studentService.checkIfStudentIsNull(1));
        assertFalse(studentService.checkIfStudentIsNull(0));
    }

}
