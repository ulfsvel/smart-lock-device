package ro.cheiafermecata.smartlock.device.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ro.cheiafermecata.smartlock.device.Data.DeviceManager;

import java.util.Map;

@Controller
public class SetUpController {


    private final DeviceManager deviceManager;


    public SetUpController(DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }


    @RequestMapping({"/","/setUp"})
    public String setUpAction(Map<String, Object> model){
        if(this.deviceManager.isDeviceSet()){
            return "redirect:/device";
        }
        try{
            this.deviceManager.getDevices();
            model.put("devices",true);
        }catch (Exception e){
            model.put("devices",false);
        }
        model.put("content","setUp/setUp");
        return "index";
    }

    @RequestMapping("/newDevice")
    public String newDeviceAction(Map<String, Object> model){
        if(this.deviceManager.isDeviceSet()){
            return "redirect:/device";
        }
        model.put("content","setUp/newDevice");
        return "index";
    }

    @RequestMapping("/newDevice/create")
    public String newDeviceCreateAction(
            Map<String, Object> model,
            @RequestParam("deviceName") String deviceName
    ){
        if(this.deviceManager.isDeviceSet()){
            return "redirect:/device";
        }
        try{
            this.deviceManager.newDevice(deviceName);
        }catch (Exception e){
            model.put("content","setUp/newDevice");
            model.put("error",e.getMessage());
            model.put("deviceName",deviceName);
            return "index";
        }
        return "redirect:/device";
    }

    @RequestMapping("/existingDevice")
    public String existingDeviceAction(Map<String, Object> model){
        if(this.deviceManager.isDeviceSet()){
            return "redirect:/device";
        }
        try{
            model.put("devices", this.deviceManager.getDevices());
        }catch (Exception e){
            return "redirect:/setUp";
        }

        model.put("content","setUp/existingDevice");
        return "index";
    }

    @RequestMapping("/existingDevice/select/{id}")
    public String existingDeviceSelectAction(
            Map<String, Object> model,
            @PathVariable("id") Long id
    ){
        if(this.deviceManager.isDeviceSet()){
            return "redirect:/device";
        }
        try{
            this.deviceManager.setDevice(id);
        }catch (Exception e){
            model.put("content","setUp/newDevice");
            model.put("error",e.getMessage());
            return "index";
        }

        return "redirect:/device";
    }


}
