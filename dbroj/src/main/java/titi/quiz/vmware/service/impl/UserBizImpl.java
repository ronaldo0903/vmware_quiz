package titi.quiz.vmware.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import titi.quiz.vmware.dao.ServiceRepository;
import titi.quiz.vmware.dao.UserRepository;
import titi.quiz.vmware.domain.Service;
import titi.quiz.vmware.domain.User;
import titi.quiz.vmware.service.UserBiz;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserBizImpl implements UserBiz {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public List<Service> getServicesByUserId(long userId) {
        return repository.getServicesByUserId(userId);
    }

    @Override
    public User getUserById(long userId) {
        return repository.findOne(userId);
    }

    @Override
    public void deleteUserSubscription(long userId, long serviceId) {
        User u = repository.findOne(userId);
        Service s = serviceRepository.findOne(serviceId);
        u.removeService(s);
        repository.save(u);
    }

    @Override
    public List<Service> getUserUnsubscribedServices(long userId) {
        List<Service> allServices = serviceRepository.findAll();
        User u = repository.findOne(userId);
        List<Service> unsubscribedServices = allServices.stream().filter(s -> !u.getServices().contains(s)).collect(Collectors.toList());
        return unsubscribedServices;
    }

    @Override
    public void addUserSubscription(long userId, long serviceId) {
        User u = repository.findOne(userId);
        Service s = serviceRepository.findOne(serviceId);
        u.addService(s);
        repository.save(u);
    }

    @Override
    public void deleteUser(long userId) {
        repository.delete(userId);
    }

    @Override
    public void createUser(String name, int age) {
        repository.save(new User(name, age));
    }
}
