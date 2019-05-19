package titi.quiz.vmware.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import titi.quiz.vmware.service.UserBiz;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserBiz userBiz;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listUsers(Model model) {
        model.addAttribute("users", userBiz.getAllUsers());
        return "users/list";
    }

    @RequestMapping(value = "/services/{userId}", method = RequestMethod.GET)
    public String listUserSusbscriptions(@PathVariable long userId, Model model) {
        model.addAttribute("services", userBiz.getServicesByUserId(userId));
        model.addAttribute("userid", userId);
        model.addAttribute("username", userBiz.getUserById(userId).getName());
        return "services/list";
    }

    @RequestMapping(value = "/{userId}/delete/{serviceId}", method = RequestMethod.GET)
    public ModelAndView deleteSubscription(@PathVariable long userId, @PathVariable long serviceId) {
        userBiz.deleteUserSubscription(userId,serviceId);
        return new ModelAndView("redirect:/users/services/" + userId);
    }

    @RequestMapping(value = "/{userId}/subscribe", method = RequestMethod.GET)
    public String listUnsubscribedServices(@PathVariable long userId, Model model) {
        model.addAttribute("userid", userId);
        model.addAttribute("services", userBiz.getUserUnsubscribedServices(userId));
        return "services/subscribe";
    }

    @RequestMapping(value = "/{userId}/subscribe/{serviceId}", method = RequestMethod.GET)
    public ModelAndView addSubscription(@PathVariable long userId, @PathVariable long serviceId) {
        userBiz.addUserSubscription(userId, serviceId);
        return new ModelAndView("redirect:/users/services/" + userId);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable long id) {
        userBiz.deleteUser(id);
        return new ModelAndView("redirect:/users");
    }

    @RequestMapping(value="/new", method = RequestMethod.GET)
    public String newProject() {
        return "users/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("name") String name, @RequestParam("age") int age) {
        userBiz.createUser(name, age);
        return new ModelAndView("redirect:/users");
    }
}