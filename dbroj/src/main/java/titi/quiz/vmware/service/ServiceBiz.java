package titi.quiz.vmware.service;

import titi.quiz.vmware.domain.Service;
import titi.quiz.vmware.domain.User;
import java.util.List;

public interface ServiceBiz {
    List<Service> getAllServices();
    Service getServiceById(long serviceId);
    List<User> getUsersByServiceId(long serviceId);
    void deleteService(long serviceId);
    void createService(String name);
}
