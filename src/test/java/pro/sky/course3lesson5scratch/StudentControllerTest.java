package pro.sky.course3lesson5scratch;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import pro.sky.course3lesson5scratch.controller.StudentController;
import pro.sky.course3lesson5scratch.model.Student;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @Autowired
    StudentController studentController; // instance of controller class under test
    @Autowired
    TestRestTemplate testRestTemplate; // instance of testing tool class to test REST requests
    @LocalServerPort
    private int port;

    @Test
    void checkContextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void viewStudentsTest() {
        Assertions.assertThat(
                        this.testRestTemplate.getForObject("http://localhost:" + port + "/student", Object.class))
                .isNotNull();
    }
}
