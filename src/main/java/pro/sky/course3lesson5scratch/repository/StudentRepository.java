package pro.sky.course3lesson5scratch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.course3lesson5scratch.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByAge(int age);

    List<Student> findByAgeBetween(int min, int max);


}
