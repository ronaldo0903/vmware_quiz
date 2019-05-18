package titi.quiz.vmware.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import titi.quiz.vmware.dao.ServiceRepository;
import titi.quiz.vmware.domain.Service;
import titi.quiz.vmware.domain.User;
import titi.quiz.vmware.service.ServiceBiz;
import titi.quiz.vmware.service.UserBiz;

import java.util.List;

@Component
public class ServiceBizImpl implements ServiceBiz {
    @Autowired
    private ServiceRepository repository;

    @Autowired

    private UserBiz userBiz;

    @Override
    public List<Service> getAllServices() {
        return repository.findAll();
    }

    @Override
    public Service getServiceById(long serviceId) {
        return repository.getOne(serviceId);
    }

    @Override
    public List<User> getUsersByServiceId(long serviceId) {
        return repository.getUsersByServiceId(serviceId);
    }

    @Override
    public void deleteService(long serviceId) {
        Service service = repository.findOne(serviceId);
        List<User> relatedUsers = repository.getUsersByServiceId(serviceId);
        relatedUsers.forEach(user -> {
            userBiz.deleteUserSubscription(user.getId(), serviceId);
        });
        repository.delete(serviceId);
    }

    @Override
    public void createService(String name) {
        repository.save(new Service(name));
    }
}
