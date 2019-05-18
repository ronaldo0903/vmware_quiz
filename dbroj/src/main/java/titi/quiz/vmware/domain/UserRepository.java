package titi.quiz.vmware.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.services from User u where u.id = :userId")
    List<Service> getServicesByUserId(@Param("userId") Long userId);
}
