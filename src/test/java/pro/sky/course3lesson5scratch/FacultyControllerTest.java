package pro.sky.course3lesson5scratch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pro.sky.course3lesson5scratch.controller.FacultyController;
import pro.sky.course3lesson5scratch.model.Faculty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = Course3Lesson5ScratchApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    FacultyController facultyController;

    @LocalServerPort
    private int port;

    @Test
    void createTest() {
        //HttpEntity<Student> requestedStudent = new HttpEntity<>(collinKerr);

        Faculty newFaculty = newRidingWhite(99);

        HttpEntity<Faculty> facultyHttpEntity = new HttpEntity<>(newFaculty);
        ResponseEntity<Faculty> responseEntity = this.testRestTemplate
                .exchange("http://localhost:" + port + "/faculty",
                        HttpMethod.POST,
                        facultyHttpEntity,
                        Faculty.class);

        Faculty actualFaculty = responseEntity.getBody();
        assertThat(actualFaculty).isNotNull();
        assertThat(actualFaculty.getName()).isEqualTo(newFaculty.getName());
        assertThat(actualFaculty.getColor()).isEqualTo(newFaculty.getColor());

    }

    private Faculty newRidingWhite(int id) {
        return new Faculty(id, "New Riding Faculty", "white");
    }

    @Test
    void updateFacultyTest() {

        Faculty faculty6 = newRidingWhite(6);
        String name6 = "Adventure Bicycling";
        String color6 = "orange";
        faculty6.setName(name6);
        faculty6.setColor(color6);
        HttpEntity<Faculty> faculty6RequestEntity = new HttpEntity<>(faculty6);

        ResponseEntity<Faculty> updatedFacultyEntity =
                this.testRestTemplate.exchange("http://localhost:" + port + "/faculty", HttpMethod.PUT,
                        faculty6RequestEntity,
                        Faculty.class);

        Faculty updatedFaculty = updatedFacultyEntity.getBody();
        assertThat(updatedFaculty).isNotNull();
        assertThat(updatedFaculty.getName()).isEqualTo(name6);
        assertThat(updatedFaculty.getColor()).isEqualTo(color6);

    }

    @Test
    void getAllTest() {
        ResponseEntity<Collection> facultyCollectionEntity =
                this.testRestTemplate.exchange("http://localhost:" + port + "/faculty", HttpMethod.GET,
                        null,
                        Collection.class);

        Collection<Faculty> actualCollection = facultyCollectionEntity.getBody();
        assertThat(actualCollection).isNotNull();
        assertThat(actualCollection.size()).isEqualTo(8);

    }

    @Test
    void getFacultyByIdTest() {
        int facultyId = 1;
        ResponseEntity<Faculty> facultyEntity =
                this.testRestTemplate.exchange("http://localhost:" + port + "/faculty/" + facultyId,
                        HttpMethod.GET,
                        null,
                        Faculty.class);

        Faculty faculty = facultyEntity.getBody();

        assertThat(faculty).isNotNull();
        System.out.println("faculty = " + faculty);
        assertThat(faculty.getName()).isEqualTo("Mountain Bike Racing");
        assertThat(faculty.getColor()).isEqualTo("green");

        faculty = this.testRestTemplate.exchange("http://localhost:" + port + "/faculty/" + 999,
                HttpMethod.GET,
                null,
                Faculty.class).getBody();
        assertThat(faculty.getName()).isNull();
        assertThat(faculty.getColor()).isNull();
    }

    @Test
    void getByColorTest() {
        String color = "white";
        ResponseEntity<List> respondedFacultyListEntity =
                this.testRestTemplate.exchange("http://localhost:" + port + "/faculty/color/" + color,
                        HttpMethod.GET,
                        null,
                        List.class);

        Object respondedObject = respondedFacultyListEntity.getBody();
        ArrayList<Object> respondedFacultyList = (ArrayList<Object>) (respondedObject);
        assertThat(respondedFacultyList.size()).isEqualTo(2);
        LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) respondedFacultyList.get(0);
        assertThat(linkedHashMap.get("color")).isEqualTo(color);

    }

    @Test
    void deleteTest() {
        long id = 7;

        ResponseEntity<Faculty> notExistedFacultyEntity =
                this.testRestTemplate.exchange("http://localhost:" + port + "/faculty/?id=" + 999,
                        HttpMethod.DELETE,
                        null,
                        Faculty.class);

        assertThat(notExistedFacultyEntity.getBody().getName()).isNull();

        ResponseEntity<Faculty> respondedFacultyEntity =
                this.testRestTemplate.exchange("http://localhost:" + port + "/faculty/?id=" + id,
                        HttpMethod.DELETE,
                        null,
                        Faculty.class);

        Faculty faculty = respondedFacultyEntity.getBody();
        assertThat(faculty.getColor()).isEqualTo("white");
        assertThat(faculty.getName()).isEqualTo("New Riding Faculty");

    }

    @Test
    void findAllByNameIgnoreCaseTest() {

        String searchPattern = "mountain BIKE Racing";

        ResponseEntity<List> respondedFacultyEntity =
                this.testRestTemplate.exchange(
                        "http://localhost:" + port + "/faculty/nameOrColorIgnoreCase?search=" + searchPattern,
                        HttpMethod.GET,
                        null,
                        List.class);

        assertThat(respondedFacultyEntity.getBody()).isNotNull();

        ArrayList<Object> list = (ArrayList<Object>) respondedFacultyEntity.getBody();
        LinkedHashMap<String, Object> actualItem = (LinkedHashMap<String, Object>) list.get(0);
        assertThat(actualItem.get("name")).isEqualTo("Mountain Bike Racing");

    }

    @Test
    void getFacultyStudentListTest() {
        String url = "http://localhost:" + port + "/faculty/students/5";
        ResponseEntity<List> respondedFacultyListEntity =
                this.testRestTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        List.class);
        ArrayList<Object> respondedFacultyList = (ArrayList<Object>) respondedFacultyListEntity.getBody();
        assertThat(respondedFacultyList.size()).isEqualTo(2);
        LinkedHashMap<String, Object> respondedItem = (LinkedHashMap<String, Object>) respondedFacultyList.get(0);
        LinkedHashMap<String, Object> facultyMap = (LinkedHashMap<String, Object>) respondedItem.get("faculty");
        assertThat(facultyMap.get("name")).isEqualTo("Stunt And Trial Bicycling");
    }

}
