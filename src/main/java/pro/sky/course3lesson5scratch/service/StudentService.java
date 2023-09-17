package pro.sky.course3lesson5scratch.service;

//import org.springframework.http.HttpStatusCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import pro.sky.course3lesson5scratch.exception.StudentAlreadyExistsException;
import pro.sky.course3lesson5scratch.exception.StudentNotFoundException;
import pro.sky.course3lesson5scratch.model.Faculty;
import pro.sky.course3lesson5scratch.model.Student;
import pro.sky.course3lesson5scratch.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final String methodInvoked = " method invoked";

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("createStudent" + methodInvoked);
        Example<Student> studentExample = Example.of(student);
        if (studentRepository.exists(studentExample)) {
            throw new StudentAlreadyExistsException();
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(long id, String newName, int newAge) {
        logger.info("updateStudent" + methodInvoked);
        Student existingStudent = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        if (newName != null) {
            existingStudent.setName(newName);
        }
        if (newAge > 15) {
            existingStudent.setAge(newAge);
        }
        return studentRepository.save(existingStudent);
    }

    public List<Student> getStudents() {
        logger.info("getStudents" + methodInvoked);
        return studentRepository.findAll();
    }

    private void loggerInfoMethodInvoked(String methodName) {
        logger.info(methodName + methodInvoked);
    }

    public Student getStudentById(long id) {
        loggerInfoMethodInvoked("getStudentById");
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    public Student sendDown(Student student) {
        loggerInfoMethodInvoked("sendDown(Student student)");
        studentRepository.delete(student);
        return student;
    }

    public Student sendDown(long id) {
        loggerInfoMethodInvoked("sendDown(long id)");
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        studentRepository.delete(student);
        return student;
    }

    public List<Student> selectedByAge(int age) {
        loggerInfoMethodInvoked("selectedByAge");
        return studentRepository.findAllByAge(age);
    }

    public List<Student> selectedByAgeBetween(int a, int b) {

        loggerInfoMethodInvoked("selectedByAgeBetween");

        if (a == b) {
            return studentRepository.findAllByAge(a);
        }

        int min, max;
        if (a < b) {
            min = a;
            max = b;
        } else {
            min = b;
            max = a;
        }

        return studentRepository.findByAgeBetween(min, max);

    }

    public Faculty getStudentFaculty(long id) {
        loggerInfoMethodInvoked("getStudentFaculty");
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        return student.getFaculty();
    }

    public Long countStudents() {
        loggerInfoMethodInvoked("countStudents");
        return studentRepository.countAll();
    }

    public Double getAverageAge() {
        loggerInfoMethodInvoked("getAverageAge");
        return studentRepository.getAverageAge();
    }

    public Double getAverageAgeByStream() {
        return studentRepository.findAll().stream().mapToInt(Student::getAge).average().orElse(-1);
    }

    public List<Student> getLast5() {
        loggerInfoMethodInvoked("getLast5");
        return studentRepository.getLastFiveStudents();
    }

    public List<String> getAllWithNamesOnA() {
        List<Student> list = studentRepository.findAll();
        return list.stream().map(Student::getName).filter(s -> s.startsWith("A")).toList();
    }

}
