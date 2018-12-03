package ro.cheiafermecata.smartlock.device.Repository;

import org.springframework.stereotype.Repository;
import ro.cheiafermecata.smartlock.device.Data.Device;

import java.io.InvalidObjectException;
import java.util.List;

@Repository
public interface DeviceRepository {

    List<Device> getDevices() throws InvalidObjectException;

    Device newDevice(String deviceName) throws InvalidObjectException;

    Device updateDeviceName(Device device) throws InvalidObjectException;

}
