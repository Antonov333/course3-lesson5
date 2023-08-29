package pro.sky.course3lesson5scratch.controller;

import org.springframework.web.bind.annotation.*;
import pro.sky.course3lesson5scratch.model.Faculty;
import pro.sky.course3lesson5scratch.model.Student;
import pro.sky.course3lesson5scratch.service.StudentService;

import java.util.List;

@RestController
@RequestMapping(path = "/student")
public class StudentController {

    private final StudentService students;

    public StudentController(StudentService students) {
        this.students = students;
    }

    @GetMapping()
    public List<Student> viewStudents() {
        return students.getStudents();
    } // covered by test


    @PostMapping()
    public Student create(@RequestBody Student student) { // covered by test
        Student createdStudent = students.createStudent(student);
        return createdStudent;
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable("id") long id) { // covered by test
        return students.getStudentById(id);
    }


    @PutMapping()
    public Student putStudent(@RequestBody Student student) { // tested
        Student updatedStudent = students.updateStudent(student.getId(), student.getName(), student.getAge());
        return updatedStudent;
    }

    @DeleteMapping()
    public Student expel(@RequestBody Student student) {
        Student expelledStudent = students.sendDown(student);
        return expelledStudent;
    }

    @DeleteMapping("/{id}")
    public Student expel(@PathVariable("id") long id) {
        Student expelledStudent = students.sendDown(id);
        return expelledStudent;
    }

    @GetMapping("/age/{age}")
    public List<Student> selectByAge(@PathVariable int age) {
        return students.selectedByAge(age);
    }

    @GetMapping("/agebetween")
    public List<Student> selectByAgeBetween(@RequestParam(name = "a") int a,
                                            @RequestParam(name = "b") int b) {
        return students.selectedByAgeBetween(a, b);
    }

    @GetMapping("/faculty/{studentId}")
    public Faculty getStudentFaculty(@PathVariable("studentId") long studentId) {
        return students.getStudentFaculty(studentId);
    }

}
