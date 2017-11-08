package yaruliy.controllers.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yaruliy.datastore.interfaces.UserService;
import yaruliy.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController{
    private UserService userService;
    @Autowired public void setUserService(UserService userService){ this.userService = userService; }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String addUser(@RequestParam(name = "name") String name,
                          @RequestParam(name = "surname") String surname,
                          @RequestParam(name = "login") String login,
                          @RequestParam(name = "password") String password,
                          @RequestParam(name = "email") String email){

        return userService.saveUser(new User(login, password, name, surname, email));
    }

    @RequestMapping(value = "get/{login}", method = RequestMethod.GET)
    public String getUser(@PathVariable String login){ return userService.findByLogin(login).toString(); }

    @RequestMapping(value = "/emails", method = RequestMethod.GET)
    public List<String> getEMails(){ return this.userService.findByLogin("login").getEmails(); }

    @RequestMapping(value = "/addemail", method = RequestMethod.POST)
    public String addEMail(@RequestParam(name = "email") String email){
        User user = this.userService.findByLogin("login");
        user.getEmails().add(email);
        return this.userService.saveUser(user);
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public String getCurrentUser(HttpServletRequest request){
        return request.getUserPrincipal().getName();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ArrayList<String> getAll(HttpServletRequest request){
        ArrayList<String> array = new ArrayList<>();
        for (int i = 0; i < userService.getAll().size(); i++) {
            if(!userService.getAll().get(i).getLogin().equals(request.getUserPrincipal().getName())){
                array.add(userService.getAll().get(i).getLogin());
            }
        }
        System.out.println(array);
        return array;
    }
}