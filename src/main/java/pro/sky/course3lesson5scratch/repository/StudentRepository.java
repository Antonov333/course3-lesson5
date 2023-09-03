package pro.sky.course3lesson5scratch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.course3lesson5scratch.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    @Query(value = "SELECT count(*) FROM student", nativeQuery = true)
    Long countAll();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Double getAverageAge();

    @Query(value = "SELECT * FROM student order by id offset (SELECT count(*) FROM student)-5\n" +
            "    limit 5", nativeQuery = true)
    List<Student> getLastFiveStudents();


}
