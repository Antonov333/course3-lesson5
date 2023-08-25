package pro.sky.course3lesson5scratch;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import pro.sky.course3lesson5scratch.controller.StudentController;
import pro.sky.course3lesson5scratch.exception.StudentNotFoundException;
import pro.sky.course3lesson5scratch.model.Faculty;
import pro.sky.course3lesson5scratch.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void createTest() {
// Student name for testing: CollinKerr

        Student collin = new Student();
        collin.setName("CollinKerr");
        collin.setAge(23);
        collin.setFaculty(new Faculty(1, "Mountain Bike Racing", "green"));

        Assertions.assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student",
                collin, Student.class)).isNotNull();


    }

    @Test
    void getStudentByIdTest() {
        Assertions.assertThat(
                        this.testRestTemplate.getForObject("http://localhost:" + port + "/student/2", Student.class))
                .isNotNull();

        Student expectedStudent = new Student();
        expectedStudent.setId(2);
        expectedStudent.setName("John");
        expectedStudent.setAge(22);

        Assertions.assertThat(
                        this.testRestTemplate.getForObject("http://localhost:" + port + "/student/2", Student.class))
                .isEqualTo(expectedStudent);

    }

    @Test
    void putStudentTest() {
        Student expectedStudent = new Student(2, "Joan", 32);
        this.testRestTemplate.put("http://localhost:" + port + "/student",
                expectedStudent);
        assertEquals(expectedStudent, testRestTemplate.
                getForObject("http://localhost:" + port + "/student/2", Student.class));
    }

    @Test
    void expelTest() {

        Student collinKerr = new Student(11, "CollinKerr", 23);
        collinKerr.setFaculty(new Faculty(1, "Mountain Bike Racing", "green"));
        this.testRestTemplate.delete("http://localhost:" + port + "/student/", collinKerr);
//       Assertions.assertThatThrownBy(() -> this.testRestTemplate.getForObject("http://localhost:" + port + "/student/" + collinKerr.getId(),
//               StudentNotFoundException.class));

    }
    /*
    @DeleteMapping()
    public Student expel(@RequestBody Student student) {
        Student expelledStudent = students.sendDown(student);
        return expelledStudent;
    }
    * */
}
