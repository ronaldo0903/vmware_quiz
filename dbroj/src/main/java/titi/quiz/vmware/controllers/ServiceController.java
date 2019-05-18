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

@Controller
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceRepository repository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String allServices(Model model) {
        model.addAttribute("services", repository.findAll());
        return "services/list";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable long id) {
        Service service = repository.findOne(id);
        List<User> relatedUsers = repository.getUsersByServiceId(id);
        relatedUsers.forEach(user -> {
            user.removeService(service);
            userRepository.save(user);
        });
        repository.delete(id);
        return new ModelAndView("redirect:/services");
    }

    @RequestMapping(value = "/users/{serviceId}", method = RequestMethod.GET)
    public String findSubscribedUsers(@PathVariable long serviceId, Model model) {
        model.addAttribute("users", repository.getUsersByServiceId(serviceId));
        model.addAttribute("serviceid", serviceId);
        return "users/list";
    }

    @RequestMapping(value="/new", method = RequestMethod.GET)
    public String newProject() {
        return "services/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("name") String name) {
        repository.save(new Service(name));
        return new ModelAndView("redirect:/services");
    }
}
