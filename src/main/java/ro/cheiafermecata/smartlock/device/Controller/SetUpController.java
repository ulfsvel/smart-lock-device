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

    /**
     * Show up the initial set up page
     * @param model the data to send to the view
     * @return the name of the view
     */
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

    /**
     * Shows the form to set up the device as a new one
     * @param model the data to send to the view
     * @return the name of the view
     */
    @RequestMapping("/newDevice")
    public String newDeviceAction(Map<String, Object> model){
        if(this.deviceManager.isDeviceSet()){
            return "redirect:/device";
        }
        model.put("content","setUp/newDevice");
        return "index";
    }

    /**
     * Creates a new device and sets it as the current one
     * @param model the data to send to the
     * @param deviceName the name of the new device
     * @return the name of the view
     */
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

    /**
     * Shows the form to set up the device as an existing one
     * @param model the data to send to the view
     * @return the name of the view
     */
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

    /**
     * Selects the device with id = {id} as the current one
     * @param model the data to send to the view
     * @param id the id of the selected device
     * @return the name of the view
     */
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
