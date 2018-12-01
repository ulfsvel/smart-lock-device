package ro.cheiafermecata.smartlock.device.Data;

import org.springframework.stereotype.Component;
import ro.cheiafermecata.smartlock.device.Repository.CredentialsRepository;
import ro.cheiafermecata.smartlock.device.Repository.DeviceRepository;
import ro.cheiafermecata.smartlock.device.WebSocket.SessionProvider;

import java.io.InvalidObjectException;
import java.util.List;

@Component
public class DeviceManager {

    private final DeviceRepository deviceRepository;

    private final SessionProvider sessionProvider;

    private final CredentialsRepository credentialsRepository;

    private Boolean isConnected = false;

    private Device device;

    private List<Device> devices;

    public DeviceManager(DeviceRepository deviceRepository, SessionProvider sessionProvider, CredentialsRepository credentialsRepository) {
        this.deviceRepository = deviceRepository;
        this.sessionProvider = sessionProvider;
        this.credentialsRepository = credentialsRepository;
        Credentials credentials = credentialsRepository.get();
        if(!credentials.getDevice().equals(0L)){
            try {
                setDevice(credentials.getDevice());
            }catch (Exception e){
                this.device = null;
                this.isConnected = false;
            }
        }
    }

    public Device getDevice() {
        return device;
    }

    private void setDevice(Device device) {
        this.device = device;
        this.connectDevice();
    }

    public void newDevice(String deviceName) throws InvalidObjectException {
        this.setDevice(this.deviceRepository.newDevice(deviceName));
    }

    public void setDevice(Long id) throws InvalidObjectException {
        this.getDevices();
        for (Device device : this.devices) {
            if (device.getId().equals(id)) {
                this.setDevice(device);
                return;
            }
        }
        throw new IndexOutOfBoundsException("The selected device does not exist");
    }

    public void unsetDevice() {
        this.disconnectDevice();
        this.device = null;
    }

    public List<Device> getDevices() throws InvalidObjectException {
        this.setDevices(this.deviceRepository.getDevices());
        if (!this.areDevicesSet()) {
            throw new IndexOutOfBoundsException("There are no devices");
        }
        return this.devices;
    }

    private void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public Boolean isDeviceSet() {
        return (this.device != null);
    }

    private Boolean areDevicesSet() throws InvalidObjectException {
        if (this.devices == null) {
            this.setDevices(this.deviceRepository.getDevices());
        }
        return !this.devices.isEmpty();
    }

    public void connectDevice() {
        Credentials credentials = credentialsRepository.get();
        credentials.setDevice(this.device.getId());
        if (this.isConnected.equals(false) && this.sessionProvider.getSession() != null) {
            this.isConnected = true;
        }
    }

    public void disconnectDevice() {
        if (this.isConnected.equals(true)) {
            this.sessionProvider.disconnect();
            this.isConnected = false;
        }
    }

    public Boolean isDeviceConnected() {
        return this.isConnected;
    }

    public void renameDevice(String deviceName) throws Exception {
        if (this.device == null) {
            throw new IndexOutOfBoundsException("The selected device does not exist");
        }
        String auxName = device.getName();
        try {
            device.setName(deviceName);
            this.device = deviceRepository.updateDeviceName(device);
        } catch (Exception e) {
            device.setName(auxName);
            throw new Exception("Unable to change device name");
        }
    }
}
