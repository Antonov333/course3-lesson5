package pro.sky.course3lesson5scratch;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.course3lesson5scratch.controller.StudentController;
import pro.sky.course3lesson5scratch.repository.AvatarRepository;
import pro.sky.course3lesson5scratch.repository.FacultyRepository;
import pro.sky.course3lesson5scratch.repository.StudentRepository;
import pro.sky.course3lesson5scratch.service.AvatarService;
import pro.sky.course3lesson5scratch.service.FacultyService;
import pro.sky.course3lesson5scratch.service.StudentService;

@WebMvcTest(StudentController.class)
public class StudentControllerTestWebMvc {

    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void nothingTest() {

    }

}
