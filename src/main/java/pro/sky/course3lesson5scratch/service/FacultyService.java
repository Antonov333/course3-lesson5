package pro.sky.course3lesson5scratch.service;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import pro.sky.course3lesson5scratch.exception.FacultyNotFoundException;
import pro.sky.course3lesson5scratch.model.Faculty;
import pro.sky.course3lesson5scratch.model.Student;
import pro.sky.course3lesson5scratch.repository.FacultyRepository;

import java.util.HashMap;
import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public List<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }

    public Faculty getById(long id) {
        return facultyRepository.findById(Long.valueOf(id)).orElseThrow(FacultyNotFoundException::new);
    }

    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findAllByColor(color);
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(long id, Faculty faculty) {
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
        return facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(search, search);
    }

    public HashMap<Long, Faculty> loadExampleFaculties() {
        final int one = 1, two = 2, three = 3, four = 4, five = 5, six = 6;
        final String silver = "silver",
                white = "white",
                green = "green",
                blue = "blue",
                black = "black",
                yellow = "yellow",
                pink = "pink";

        facultyRepository.save(new Faculty(Long.valueOf(one), "Commercial Cycling", silver));
        facultyRepository.save(new Faculty(Long.valueOf(two), "Road Bicycle Racing", yellow));
        facultyRepository.save(new Faculty(Long.valueOf(three), "Mountain Bike Racing", green));
        facultyRepository.save(new Faculty(Long.valueOf(four), "Bikepacking", blue));

        HashMap<Long, Faculty> facultyHashMap = new HashMap<>();
        facultyRepository.findAll().stream().map(faculty -> facultyHashMap.put(Long.valueOf(faculty.getId()), faculty));
        return facultyHashMap;
    }

    public Faculty deleteFaculty(Long id) {
        Faculty deletedFaculty =
                facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
        facultyRepository.deleteById(id);
        return deletedFaculty;
    }

    public Faculty deleteFaculty(Faculty faculty) {
        Example<Faculty> facultyExample = Example.of(faculty);
        Faculty deletedFaculty =
                facultyRepository.findOne(facultyExample).orElseThrow(FacultyNotFoundException::new);
        facultyRepository.delete(faculty);
        return deletedFaculty;
    }

    public List<Student> getStudentList(long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(FacultyNotFoundException::new);
        return faculty.getStudents();
    }

}
