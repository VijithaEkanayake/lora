package yaruliy.controllers.views;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PageController{
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String messagesPage(){ return "messages"; }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(){ return "login"; }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationPage(){ return "registration"; }

    @RequestMapping(value = "/device/*", method = RequestMethod.GET)
    public String devicePage(){ return "device"; }

    @RequestMapping(value = {"/devices"}, method = RequestMethod.GET)
    public String devicesPage(HttpServletRequest request){
        //System.out.println("from pageControll: " + request.getUserPrincipal().getName());
        return "devices";
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String settingsPage(){ return "settings"; }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) new SecurityContextLogoutHandler().logout(request, response, auth);
        return "redirect:/login";
    }

    @RequestMapping(value = {"/", "/chat"}, method = RequestMethod.GET)
    public String chatPage(){ return "chat"; }

    @RequestMapping(value = "/carinfo", method = RequestMethod.GET)
    public String carInfoPage(){ return "carinfo"; }
}