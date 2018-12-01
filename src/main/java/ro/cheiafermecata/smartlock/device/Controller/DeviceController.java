package ro.cheiafermecata.smartlock.device.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ro.cheiafermecata.smartlock.device.Data.DeviceManager;

import java.util.Map;

@Controller
public class DeviceController {

    private final DeviceManager deviceManager;

    public DeviceController(DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    @RequestMapping("/device/rename")
    public String renameFormAction(Map<String, Object> model) {
        if (!this.deviceManager.isDeviceSet()) {
            return "redirect:/setUp";
        }
        model.put("deviceName", deviceManager.getDevice().getName());
        model.put("content", "device/rename");
        return "index";
    }


    @RequestMapping("/device/rename/do")
    public String renameAction(
            Map<String, Object> model,
            @RequestParam("deviceName") String deviceName
    ) {
        if (!this.deviceManager.isDeviceSet()) {
            return "redirect:/setUp";
        }
        try {
            this.deviceManager.renameDevice(deviceName);
            return "redirect:/device";
        } catch (Exception e) {
            model.put("deviceName", deviceManager.getDevice().getName());
            model.put("error", e.getMessage());
            model.put("content", "device/rename");
            return "index";
        }
    }

    @RequestMapping("/device/connect")
    public String deviceConnectAction(Map<String, Object> model) {
        if (!this.deviceManager.isDeviceSet()) {
            return "redirect:/setUp";
        }
        return changeDeviceState(model, true);
    }

    @RequestMapping("/device/disconnect")
    public String deviceDisconnectAction(Map<String, Object> model) {
        if (!this.deviceManager.isDeviceSet()) {
            return "redirect:/setUp";
        }
        return changeDeviceState(model, false);
    }

    private String changeDeviceState(Map<String, Object> model, Boolean state) {
        try {
            if (state) {
                this.deviceManager.connectDevice();
            } else {
                this.deviceManager.disconnectDevice();
            }
        } catch (Exception e) {
            model.put("deviceName", deviceManager.getDevice().getName());
            model.put("isConnected", this.deviceManager.isDeviceConnected());
            model.put("error", e.getMessage());
            model.put("content", "device/device");
            return "index";
        }
        return "redirect:/device";
    }

    @RequestMapping("/device")
    public String deviceAction(Map<String, Object> model) {
        if (!this.deviceManager.isDeviceSet()) {
            return "redirect:/setUp";
        }
        model.put("deviceName", this.deviceManager.getDevice().getName());
        model.put("isConnected", this.deviceManager.isDeviceConnected());

        model.put("content", "device/device");
        return "index";
    }

    @RequestMapping("/device/unset")
    public String unsetAction() {
        if (!this.deviceManager.isDeviceSet()) {
            return "redirect:/setUp";
        }
        this.deviceManager.unsetDevice();
        return "redirect:/setUp";
    }

}
