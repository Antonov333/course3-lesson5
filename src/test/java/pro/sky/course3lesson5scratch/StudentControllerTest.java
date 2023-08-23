package pro.sky.course3lesson5scratch;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import pro.sky.course3lesson5scratch.controller.StudentController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    StudentController studentController; // instance of controller class under test

    @Autowired
    TestRestTemplate testRestTemplate; // instance of testing tool class to test REST requests

    @Test
    void checkContextLoads() throws Exception {
        System.out.println("port = " + port);
        Assertions.assertThat(studentController).isNotNull();
    }
}
