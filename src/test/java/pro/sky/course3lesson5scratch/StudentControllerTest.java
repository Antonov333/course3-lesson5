package pro.sky.course3lesson5scratch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.sky.course3lesson5scratch.controller.StudentController;
import pro.sky.course3lesson5scratch.exception.StudentNotFoundException;
import pro.sky.course3lesson5scratch.model.Faculty;
import pro.sky.course3lesson5scratch.model.Student;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThat(studentController).isNotNull();
    }

    @Test
    void viewStudentsTest() {
        assertThat(
                this.testRestTemplate.getForObject("http://localhost:" + port + "/student", Object.class))
                .isNotNull();
    }

    @Test
    void createTest() {

        Student student = new Student();
        Faculty faculty = new Faculty(1, "Mountain Bike Racing", "green");
        student.setName("Alvarado");
        student.setAge(19);
        student.setFaculty(faculty);

        ResponseEntity<Student> studentResponse = createStudent(student, faculty);

        assertThat(studentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentResponse.getBody()).isNotNull();
        assertThat(studentResponse.getBody().getName()).isEqualTo(student.getName());
        assertThat(studentResponse.getBody().getAge()).isEqualTo(student.getAge());
    }

    private ResponseEntity<Student> createStudent(String name, int age, Faculty faculty) {
        return testRestTemplate.postForEntity("/student",
                new Student(0, name, age, faculty),
                Student.class);
    }

    private ResponseEntity<Student> createStudent(Student student, Faculty faculty) {
        Student s = student;
        s.setFaculty(faculty);
        return testRestTemplate.postForEntity("/student", s, Student.class);
    }

    @Test
    void getStudentByIdTest() {
        assertThat(
                this.testRestTemplate.getForObject("http://localhost:" + port + "/student/2", Student.class))
                .isNotNull();

        Student expectedStudent = new Student();
        expectedStudent.setId(2);
        expectedStudent.setName("Joan");
        expectedStudent.setAge(32);

        assertThat(
                this.testRestTemplate.getForObject("http://localhost:" + port + "/student/2", Student.class))
                .isEqualTo(expectedStudent);

    }

    @Test
    void putStudentTest() {
        Student expectedStudent = new Student(2, "Joan", 23);
        this.testRestTemplate.put("http://localhost:" + port + "/student",
                expectedStudent);
        assertEquals(expectedStudent, testRestTemplate.
                getForObject("http://localhost:" + port + "/student/2", Student.class));
    }

    /*
    @DeleteMapping("/{id}")
    public Student expel(@PathVariable("id") long id) {
        Student expelledStudent = students.sendDown(id);
        return expelledStudent;
    }

     */

    @Test
    void expelByIdTest() {
        Long id = 12L;
        this.testRestTemplate.delete("http://localhost:" + port + "/student/" + id);
        assertThrows(StudentNotFoundException.class, () -> this.studentController.getStudent(id));
    }

    @Test
    void expelTest() {

        Student collinKerr = new Student(11, "CollinKerr", 23);
        collinKerr.setFaculty(new Faculty(1, "Mountain Bike Racing", "green"));
        HttpEntity<Student> requestedStudent = new HttpEntity<>(collinKerr);
        ResponseEntity<Student> actualStudent =
                this.testRestTemplate.exchange("http://localhost:" + port + "/student/",
                        HttpMethod.DELETE,
                        requestedStudent,
                        Student.class);
        assertEquals(requestedStudent.getBody(), actualStudent.getBody());
    }

    @Test
    void selectByAgeTest() {
        int testAge = 23;
        Student expectedStudent = this.testRestTemplate
                .getForEntity("http://localhost:" + port + "/student/2", Student.class).getBody();
        ResponseEntity<ArrayList> response = this.testRestTemplate.getForEntity(
                "http://localhost:" + port + "/student/age/" + testAge, ArrayList.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(null, response.getBody());
        assertThat(response.getBody().size()).isEqualTo(1);
        LinkedHashMap<String, Object> mapResponse = (LinkedHashMap) response.getBody().get(0);
        String myKey = "age";
        assertThat(mapResponse.containsValue(testAge)).isTrue();
        assertThat(mapResponse.get(myKey)).isEqualTo(testAge);
    }

    @Test
    void selectByAgeBetweenTest() {
        int min = 24, max = 24;
        // http://localhost:8080/student/agebetween?a=18&b=24

        ResponseEntity responseEntity = this.testRestTemplate.exchange(
                "http://localhost:" + port + "/student/agebetween?a=18&b=24", HttpMethod.GET, null,
                ArrayList.class);
        System.out.println("responseEntity.getBody().getClass() = " + responseEntity.getBody().getClass());
        ArrayList<LinkedHashMap> responseBodyArrayList = (ArrayList<LinkedHashMap>) responseEntity.getBody();
        System.out.println("responseBodyArrayList = " + responseBodyArrayList);
        assertThat(responseBodyArrayList.size()).isEqualTo(6);
    }

}
