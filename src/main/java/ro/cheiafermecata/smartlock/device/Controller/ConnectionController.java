package ro.cheiafermecata.smartlock.device.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class ConnectionController {

    @RequestMapping("/")
    public String homeAction(Map<String, Object> model){
        model.put("content","setUp");
        return "index";
    }

    @RequestMapping("/newDevice")
    public String newDeviceAction(Map<String, Object> model){
        model.put("content","newDevice");
        return "index";
    }

    @RequestMapping("/existingDevice")
    public String existingDeviceAction(Map<String, Object> model){
        model.put("content","existingDevice");
        return "index";
    }

}
