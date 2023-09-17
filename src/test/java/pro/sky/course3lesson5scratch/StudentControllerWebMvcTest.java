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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.course3lesson5scratch.controller.StudentController;
import pro.sky.course3lesson5scratch.model.Student;
import pro.sky.course3lesson5scratch.repository.StudentRepository;
import pro.sky.course3lesson5scratch.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void getStudentTest() throws Exception {
        Student john25 = new Student(0, "John", 25);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(john25));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/student/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(john25.getName()))
                .andExpect(jsonPath("$.age").value(john25.getAge()));
    }

    @Test
    void getStudentTest2() throws Exception {
        Student john25 = new Student(0, "John", 25);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(john25));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/student/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(john25.getName()))
                .andExpect(jsonPath("$.age").value(john25.getAge()));

        System.out.println("resultActions.toString() = " + resultActions.toString());
    }

    private Student getJohn25() {
        return new Student(0, "John", 25);
    }

    @Test
    void createTest() throws Exception {
        Student student = getJohn25();

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .content(objectMapper.writeValueAsString(student))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    void putStudentTest() throws Exception {
        Student student = getJohn25();

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.put("/student")
                        .content(objectMapper.writeValueAsString(student))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    void expelByStudentTest() throws Exception {
        Student student = getJohn25();
        doNothing().when(studentRepository).delete(any(Student.class));

        when(studentService.sendDown(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.delete("/student")
                        .content(objectMapper.writeValueAsString(student))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));

    }

    @Test
    void expelByIdTest() throws Exception {
        Student student = getJohn25();
        when(studentRepository.findById(any())).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(any(Student.class));
        when(studentService.sendDown(1)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/1")
                        .content(objectMapper.writeValueAsString(student))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));

    }

    @Test
    void selectByAgeTest() throws Exception {
        Student student1 = new Student(1, "John", 25);
        Student student2 = new Student(2, "Joan", 25);

        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        when(studentRepository.findAllByAge(25)).thenReturn(studentList);
        mockMvc.perform(MockMvcRequestBuilders.get("/student/age/25")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").value(student1))
                .andExpect(jsonPath("$.[1]").value(student2))
        ;
    }

    @Test
    void selectByAgeBetweenTest() throws Exception {
        Student student1 = new Student(1, "John", 24);
        Student student2 = new Student(2, "Joan", 22);
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        int a = 20, b = 25;

        when(studentRepository.findByAgeBetween(a, b)).thenReturn(studentList);
        mockMvc.perform(MockMvcRequestBuilders.get("/student/agebetween?a=20&b=25")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").value(student1))
                .andExpect(jsonPath("$.[1]").value(student2))
        ;
    }

}
