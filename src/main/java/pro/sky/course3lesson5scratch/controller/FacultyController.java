package pro.sky.course3lesson5scratch.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.course3lesson5scratch.model.Faculty;
import pro.sky.course3lesson5scratch.model.Student;
import pro.sky.course3lesson5scratch.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public Collection<Faculty> getAll() {
        return facultyService.getFaculties();
    }

    @GetMapping(path = "/{id}")
    public Faculty getFacultyById(@PathVariable long id) {
        return facultyService.getById(id);
    }

    @GetMapping(path = "/color/{color}")
    public List<Faculty> getByColor(@PathVariable String color) {
        return facultyService.getFacultiesByColor(color);
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.updateFaculty(faculty.getId(), faculty);
        if (updatedFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping()
    public Faculty delete(@RequestParam("id") long id) {
        return facultyService.deleteFaculty(id);
    }

    @GetMapping(path = "/nameOrColorIgnoreCase")
    public List<Faculty> findAllByNameIgnoreCase(@RequestParam(name = "search") String search) {
        return facultyService.findByNameOrColorCaseTolerant(search);
    }

    @GetMapping(path = "/students/{facultyId}")
    public List<Student> getFacultyStudentList(@PathVariable("facultyId") long facultyId) {
        return facultyService.getStudentList(facultyId);
    }

    @GetMapping(path = "/get-longest-name")
    public String getLongestName() {
        return facultyService.getLongestNameByStream();
    }

}