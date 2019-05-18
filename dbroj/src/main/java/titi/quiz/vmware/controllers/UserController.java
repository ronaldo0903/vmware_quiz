package titi.quiz.vmware.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import titi.quiz.vmware.domain.User;
import titi.quiz.vmware.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listUsers(Model model) {
        model.addAttribute("users", repository.findAll());
        return "users/list";
    }

    @RequestMapping(value = "/services/{userId}", method = RequestMethod.GET)
    public String listUserSusbscriptions(@PathVariable long userId, Model model) {
        model.addAttribute("services", repository.getServicesByUserId(userId));
        return "services/list";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable long id) {
        repository.deleteById(id);
        return new ModelAndView("redirect:/users/list");
    }

    @RequestMapping(value="/new", method = RequestMethod.GET)
    public String newProject() {
        return "users/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("name") String name, @RequestParam("age") int age) {
        repository.save(new User(name, age));
        return new ModelAndView("redirect:/users/list");
    }
}