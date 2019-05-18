package titi.quiz.vmware.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import titi.quiz.vmware.domain.Service;
import titi.quiz.vmware.domain.ServiceRepository;
import titi.quiz.vmware.domain.User;
import titi.quiz.vmware.domain.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ServiceRepository serviceRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listUsers(Model model) {
        model.addAttribute("users", repository.findAll());
        return "users/list";
    }

    @RequestMapping(value = "/services/{userId}", method = RequestMethod.GET)
    public String listUserSusbscriptions(@PathVariable long userId, Model model) {
        model.addAttribute("services", repository.getServicesByUserId(userId));
        model.addAttribute("userid", userId);
        model.addAttribute("username", repository.findOne(userId).getName());
        return "services/list";
    }

    @RequestMapping(value = "/{userId}/delete/{serviceId}", method = RequestMethod.GET)
    public ModelAndView deleteSubscription(@PathVariable long userId, @PathVariable long serviceId) {
        User u = repository.findOne(userId);
        Service s = serviceRepository.findOne(serviceId);
        u.removeService(s);
        repository.save(u);
        return new ModelAndView("redirect:/users/services/" + userId);
    }

    @RequestMapping(value = "/{userId}/subscribe", method = RequestMethod.GET)
    public String listUnsubscribedServices(@PathVariable long userId, Model model) {
        List<Service> allServices = serviceRepository.findAll();
        User u = repository.findOne(userId);
        List<Service> unsubscribedServices = allServices.stream().filter(s -> !u.getServices().contains(s)).collect(Collectors.toList());
        model.addAttribute("userid", userId);
        model.addAttribute("services", unsubscribedServices);
        return "services/subscribe";
    }

    @RequestMapping(value = "/{userId}/subscribe/{serviceId}", method = RequestMethod.GET)
    public ModelAndView addSubscription(@PathVariable long userId, @PathVariable long serviceId) {
        User u = repository.findOne(userId);
        Service s = serviceRepository.findOne(serviceId);
        u.addService(s);
        repository.save(u);
        return new ModelAndView("redirect:/users/services/" + userId);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable long id) {
        repository.delete(id);
        return new ModelAndView("redirect:/users");
    }

    @RequestMapping(value="/new", method = RequestMethod.GET)
    public String newProject() {
        return "users/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("name") String name, @RequestParam("age") int age) {
        repository.save(new User(name, age));
        return new ModelAndView("redirect:/users");
    }
}