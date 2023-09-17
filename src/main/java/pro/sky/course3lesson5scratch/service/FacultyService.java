package pro.sky.course3lesson5scratch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import pro.sky.course3lesson5scratch.exception.FacultyNotFoundException;
import pro.sky.course3lesson5scratch.model.Faculty;
import pro.sky.course3lesson5scratch.model.Student;
import pro.sky.course3lesson5scratch.repository.FacultyRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Service
public class FacultyService {

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    private void loggerInfoMethodInvoked(String methodName) {
        logger.info(methodName + " method invoked");
    }

    public List<Faculty> getFaculties() {
        loggerInfoMethodInvoked("getFaculties");
        return facultyRepository.findAll();
    }

    public Faculty getById(long id) {
        loggerInfoMethodInvoked("getById");
        return facultyRepository.findById(Long.valueOf(id)).orElseThrow(FacultyNotFoundException::new);
    }

    public List<Faculty> getFacultiesByColor(String color) {
        loggerInfoMethodInvoked("getFacultiesByColor");
        return facultyRepository.findAllByColor(color);
    }

    public Faculty createFaculty(Faculty faculty) {
        loggerInfoMethodInvoked("createFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(long id, Faculty faculty) {
        loggerInfoMethodInvoked("updateFaculty");
        Faculty existingFaculty = facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
        if (faculty.getName() != null) {
            existingFaculty.setName(faculty.getName());
        }
        if (faculty.getColor() != null) {
            existingFaculty.setColor(faculty.getColor());
        }
        return facultyRepository.save(existingFaculty);
    }

    public List<Faculty> findByNameOrColorCaseTolerant(String search) {
        loggerInfoMethodInvoked("findByNameOrColorCaseTolerant");
        return facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(search, search);
    }

    public Faculty deleteFaculty(Long id) {
        loggerInfoMethodInvoked("deleteFaculty(Long)");
        Faculty deletedFaculty =
                facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
        facultyRepository.deleteById(id);
        return deletedFaculty;
    }

    public Faculty deleteFaculty(Faculty faculty) {
        loggerInfoMethodInvoked("deleteFaculty(Faculty)");
        Example<Faculty> facultyExample = Example.of(faculty);
        Faculty deletedFaculty =
                facultyRepository.findOne(facultyExample).orElseThrow(FacultyNotFoundException::new);
        facultyRepository.delete(faculty);
        return deletedFaculty;
    }

    public List<Student> getStudentList(long facultyId) {
        loggerInfoMethodInvoked("getStudentList");
        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(FacultyNotFoundException::new);
        return faculty.getStudents();
    }

    public String getLongestNameByStream() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName).max(Comparator.comparingInt(String::length)).orElse("-");
    }

}
