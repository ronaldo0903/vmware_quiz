package titi.quiz.vmware.service;

import titi.quiz.vmware.domain.Service;
import titi.quiz.vmware.domain.User;

import java.util.List;

public interface UserBiz {
    List<User> getAllUsers();
    List<Service> getServicesByUserId(long userId);
    User getUserById(long userId);
    void deleteUserSubscription(long userId, long serviceId);
    List<Service> getUserUnsubscribedServices(long userId);
    void addUserSubscription(long userId, long serviceId);
    void deleteUser(long userId);
    void createUser(String name, int age);


}
