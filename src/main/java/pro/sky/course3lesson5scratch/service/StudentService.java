package pro.sky.course3lesson5scratch.service;

//import org.springframework.http.HttpStatusCode;

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

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        Example<Student> studentExample = Example.of(student);
        if (studentRepository.exists(studentExample)) {
            throw new StudentAlreadyExistsException();
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(long id, String newName, int newAge) {
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
        return studentRepository.findAll();
    }

    public Student getStudentById(long id) {
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    public Student sendDown(Student student) {
        studentRepository.delete(student);
        return student;
    }

    public Student sendDown(long id) {
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        studentRepository.delete(student);
        return student;
    }

    public List<Student> selectedByAge(int age) {
        return studentRepository.findAllByAge(age);
    }

    public List<Student> selectedByAgeBetween(int a, int b) {

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
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        return student.getFaculty();
    }

}
