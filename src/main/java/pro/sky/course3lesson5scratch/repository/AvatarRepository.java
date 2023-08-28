package pro.sky.course3lesson5scratch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.course3lesson5scratch.model.Avatar;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
