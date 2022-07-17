package com.techleads.app;

import com.techleads.app.models.CollegeStudent;
import com.techleads.app.repository.StudentRepository;
import com.techleads.app.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradebookControllerTest {

    private static MockHttpServletRequest mockHttpServletRequest;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private StudentAndGradeService studentAndGradeServiceMock;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeAll
    static void beforeAll(){
        mockHttpServletRequest=new MockHttpServletRequest();
        mockHttpServletRequest.addParameter("firstname","madhav");
        mockHttpServletRequest.addParameter("lastname","anupoju");
        mockHttpServletRequest.addParameter("emailAddress","madhav@techm.com");

    }

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("insert into student(id, firstname, lastname, email_address) values(1, 'dill', 'anupoju', 'dill@us.com')");
    }


    @Test
    void getStudentHttpRequest() throws Exception {
        CollegeStudent studentOne = new CollegeStudent("madhav", "anupoju", "madhav@techm.com");
        CollegeStudent studentTwo = new CollegeStudent("dill", "anupoju", "dill@usa.com");
        CollegeStudent studentThree = new CollegeStudent("sarath", "singupuram", "sarath@adp.com");
        List<CollegeStudent> collegeStudents = Arrays.asList(studentOne, studentTwo, studentThree);
        when(studentAndGradeServiceMock.getGradeBook()).thenReturn(collegeStudents);
        assertIterableEquals(collegeStudents, studentAndGradeServiceMock.getGradeBook());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "index");

    }

    @Test
    void createStudentHttpRequest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstname", mockHttpServletRequest.getParameterValues("firstname"))
                        .param("lastname", mockHttpServletRequest.getParameterValues("lastname"))
                        .param("emailAddress", mockHttpServletRequest.getParameterValues("emailAddress")))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "index");

        CollegeStudent byEmailAddress = studentRepository.findByEmailAddress("madhav@techm.com");
        assertNotNull(byEmailAddress);


    }

    @AfterEach
    void afterEach() {
        jdbcTemplate.execute("delete from student");
    }

}
