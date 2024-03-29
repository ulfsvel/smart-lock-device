package ro.cheiafermecata.smartlock.device.Repository.Implementations;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ro.cheiafermecata.smartlock.device.Data.Credentials;
import ro.cheiafermecata.smartlock.device.Data.Device;
import ro.cheiafermecata.smartlock.device.Repository.CredentialsRepository;
import ro.cheiafermecata.smartlock.device.Repository.DeviceRepository;
import ro.cheiafermecata.smartlock.device.Repository.UrlRepository;

import java.io.InvalidObjectException;
import java.util.List;

@Component
public class RestDeviceRepository implements DeviceRepository {

    private String GET_URL = "/deviceDetails";

    private String NEW_URL = "/deviceDetails/new";

    private String SAVE_URL = "/deviceDetails/rename";

    private final RestTemplate restTemplate;

    private final CredentialsRepository credentialsRepository;

    private HttpHeaders headers = null;

    public RestDeviceRepository(RestTemplate restTemplate, CredentialsRepository credentialsRepository, UrlRepository urlRepository) {
        this.restTemplate = restTemplate;
        this.credentialsRepository = credentialsRepository;
        GET_URL = urlRepository.apiUrl() + GET_URL;
        NEW_URL = urlRepository.apiUrl() + NEW_URL;
        SAVE_URL = urlRepository.apiUrl() + SAVE_URL;
    }

    /**
     * Uses the RestHelper to get the appropriate HttpHeaders
     * @return the resulting HttpHeaders
     */
    private HttpHeaders getAuthHeader() {
        if (this.headers == null) {
            Credentials credentials = this.credentialsRepository.get();
            this.headers = RestHelper.getAuthHeader(credentials.getUsername(),credentials.getPassword());
        }
        return this.headers;
    }

    /**
     * Gets all devices from the central server
     * @return a List of Devices
     * @throws InvalidObjectException if no valid response was received
     */
    @Override
    public List<Device> getDevices() throws InvalidObjectException {
        List<Device> devices = restTemplate.exchange(
                GET_URL,
                HttpMethod.GET,
                new HttpEntity<>(this.getAuthHeader()),
                new ParameterizedTypeReference<List<Device>>() {
                }).getBody();
        if (devices == null) {
            throw new InvalidObjectException("No valid data was returned");
        }
        return devices;
    }

    /**
     * Creates a new device on the central server and gets it
     * @param deviceName the name of the device to be created
     * @return the new device
     * @throws InvalidObjectException if no valid data was received
     */
    @Override
    public Device newDevice(String deviceName) throws InvalidObjectException {
        HttpHeaders headers = this.getAuthHeader();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(NEW_URL)
                .queryParam("name", deviceName);
        Device device = restTemplate.exchange(
                builder.build().toString(),
                HttpMethod.POST,
                new HttpEntity<>(headers),
                Device.class).getBody();
        if (device == null) {
            throw new InvalidObjectException("No valid data was returned");
        }
        return device;
    }

    /**
     * Changed the name of the device with id = {device.id} to {device.name}
     * @param device the device to change
     * @return the resulting device
     * @throws InvalidObjectException if no valid data was received
     */
    @Override
    public Device updateDeviceName(Device device) throws InvalidObjectException {
        HttpHeaders headers = this.getAuthHeader();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SAVE_URL)
                .queryParam("id", device.getId())
                .queryParam("name", device.getName());
        Device response = restTemplate.exchange(
                builder.build().toString(),
                HttpMethod.POST,
                new HttpEntity<>(headers),
                Device.class).getBody();
        if (response == null) {
            throw new InvalidObjectException("No valid data was returned");
        }
        return response;
    }

}
