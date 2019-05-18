package titi.quiz.vmware.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import titi.quiz.vmware.domain.Service;
import titi.quiz.vmware.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.services from User u where u.id = :userId")
    List<Service> getServicesByUserId(@Param("userId") Long userId);
}
