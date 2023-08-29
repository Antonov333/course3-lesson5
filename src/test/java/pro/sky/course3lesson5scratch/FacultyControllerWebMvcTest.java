package pro.sky.course3lesson5scratch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.course3lesson5scratch.controller.FacultyController;
import pro.sky.course3lesson5scratch.controller.StudentController;
import pro.sky.course3lesson5scratch.model.Faculty;
import pro.sky.course3lesson5scratch.repository.FacultyRepository;
import pro.sky.course3lesson5scratch.repository.StudentRepository;
import pro.sky.course3lesson5scratch.service.FacultyService;
import pro.sky.course3lesson5scratch.service.StudentService;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.postgresql.hostchooser.HostRequirement.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    void getAllTest() throws Exception {

        when(facultyRepository.findAll()).thenReturn(sampleFacultyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").value(getFaculty1()))
                .andExpect(jsonPath("$[1]").value(getFaculty2()))
        ;
    }

    @Test
    void getFacultyByIdTest() throws Exception {
        long id = 1;
        when(facultyRepository.findById(id)).thenReturn(Optional.of(getFaculty1()));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(getFaculty1()));
    }

    @Test
    void getByColorTest() throws Exception {
        Faculty faculty2 = getFaculty2();
        String color = getFaculty1().getColor();
        faculty2.setColor("green");
        when(facultyRepository.findAllByColor(any(String.class))).thenReturn(Arrays.asList(getFaculty1(), faculty2));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/color/" + color)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].color").value(color))
                .andExpect(jsonPath("$[1].color").value(color))
        ;
    }

    @Test
    void createFacultyTest() throws Exception {
        Faculty newFaculty = getFaculty3();
        when(facultyRepository.save(any(Faculty.class))).thenReturn(newFaculty);
        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .content(objectMapper.writeValueAsString(newFaculty))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newFaculty.getName()))
                .andExpect(jsonPath("$.color").value(newFaculty.getColor()));
    }

    @Test
    void updateFacultyTest() throws Exception {
        Faculty newFaculty = getFaculty3();
        when(facultyRepository.save(any(Faculty.class))).thenReturn(newFaculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(newFaculty));
        mockMvc.perform(MockMvcRequestBuilders.put("/faculty")
                        .content(objectMapper.writeValueAsString(newFaculty))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newFaculty.getName()))
                .andExpect(jsonPath("$.color").value(newFaculty.getColor()));
    }

    @Test
    void deleteTest() throws Exception {
        Faculty faculty = getFaculty3();
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty?id=" + getFaculty3().getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findAllByNameIgnoreCaseTest() throws Exception {
        List<Faculty> facultyList = sampleFacultyList();
        String color = "green";
        facultyList.get(0).setColor(color);
        facultyList.get(1).setColor(color);
        when(facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(any(String.class), any(String.class)))
                .thenReturn(facultyList);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/nameOrColorIgnoreCase?search=gREen")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].color").value(color))
                .andExpect(jsonPath("$[1].color").value(color));

    }

    private List<Faculty> sampleFacultyList() {
        List<Faculty> list = new ArrayList<>();
        list.add(getFaculty1());
        list.add(getFaculty2());
        return list;
    }

    private Faculty getFaculty1() {
        return new Faculty(1, "Mountain Bike Racing", "green");
    }

    private Faculty getFaculty2() {
        return new Faculty(2, "Road Bicycle Racing", "yellow");
    }

    private Faculty getFaculty3() {
        return new Faculty(3, "Faculty #3", "gray");
    }

}
