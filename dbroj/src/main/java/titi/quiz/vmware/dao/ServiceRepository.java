package titi.quiz.vmware.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import titi.quiz.vmware.domain.Service;
import titi.quiz.vmware.domain.User;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    @Query("SELECT s.users from Service s WHERE s.id = :serviceId")
    List<User> getUsersByServiceId(@Param("serviceId") Long serviceId);
}
