package titi.quiz.vmware.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    @Query("SELECT s.users from Service s WHERE s.id = :serviceId")
    List<User> getUsersByServiceId(@Param("serviceId") Long serviceId);
}
