package ro.cheiafermecata.smartlock.device.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * Show the rename form for the current device
     * @param model the data to send to the view
     * @return the name of the view
     */
    @GetMapping("/device/rename")
    public String renameFormAction(Map<String, Object> model) {
        if (!this.deviceManager.isDeviceSet()) {
            return "redirect:/setUp";
        }
        model.put("deviceName", deviceManager.getDevice().getName());
        model.put("content", "device/rename");
        return "index";
    }

    /**
     * Performs the rename action
     * @param model the data to send to the user
     * @param deviceName the new name of the device
     * @return the name of the view
     */
    @PostMapping("/device/rename")
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

    /**
     * Connects the device to the central server
     * @param model the data to send to the view
     * @return the name of the view
     */
    @RequestMapping("/device/connect")
    public String deviceConnectAction(Map<String, Object> model) {
        if (!this.deviceManager.isDeviceSet()) {
            return "redirect:/setUp";
        }
        return changeDeviceState(model, true);
    }

    /**
     * Disconnects the device from the central server
     * @param model the data to send to the view
     * @return the name of the view
     */
    @RequestMapping("/device/disconnect")
    public String deviceDisconnectAction(Map<String, Object> model) {
        if (!this.deviceManager.isDeviceSet()) {
            return "redirect:/setUp";
        }
        return changeDeviceState(model, false);
    }

    /**
     * Connects or disconnects from the central server
     * @param model the data to send to the view
     * @param state true to connect, false to disconnect
     * @return the name of the view
     */
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

    /**
     * Shows the control panel for the current device
     * @param model the data to send to the view
     * @return the name of the view
     */
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

    /**
     * Removes the current device from memory redirects to set up
     * @return the name of the view
     */
    @RequestMapping("/device/unset")
    public String unsetAction() {
        if (!this.deviceManager.isDeviceSet()) {
            return "redirect:/setUp";
        }
        this.deviceManager.unsetDevice();
        return "redirect:/setUp";
    }

}
