package ro.cheiafermecata.smartlock.device.Repository;

import org.springframework.stereotype.Repository;
import ro.cheiafermecata.smartlock.device.Data.Device;

import java.io.InvalidObjectException;
import java.util.List;

@Repository
public interface DeviceRepository {

    /**
     * Gets all the devices for the current user
     * @return a List of Devices
     * @throws InvalidObjectException if there is no valid data
     */
    List<Device> getDevices() throws InvalidObjectException;

    /**
     * Creates a new device
     * @param deviceName the name of the new device
     * @return the new device
     * @throws InvalidObjectException if there was an error creating the device
     */
    Device newDevice(String deviceName) throws InvalidObjectException;

    /**
     * Updates the name of the given device
     * @param device the device to update
     * @return the resulting device
     * @throws InvalidObjectException if there was an error updating the device
     */
    Device updateDeviceName(Device device) throws InvalidObjectException;

}
