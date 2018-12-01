package ro.cheiafermecata.smartlock.device.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String showLogin(Map<String, Object> model, @RequestParam(name = "error", required = false) String error) {
        if(error != null){
            model.put("error","Authentication failed");
        }
        model.put("content","login/login");
        return "index";
    }

}
