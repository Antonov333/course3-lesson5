package pro.sky.course3lesson5scratch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.course3lesson5scratch.model.Faculty;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findAllByColor(String color);

    List<Faculty> findAllByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

//    Optional<Faculty> findByStudent_Id(Long studentId);

}
