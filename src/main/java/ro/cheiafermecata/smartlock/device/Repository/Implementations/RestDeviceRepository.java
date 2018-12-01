package ro.cheiafermecata.smartlock.device.Repository.Implementations;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ro.cheiafermecata.smartlock.device.Config.Urls;
import ro.cheiafermecata.smartlock.device.Data.Credentials;
import ro.cheiafermecata.smartlock.device.Data.Device;
import ro.cheiafermecata.smartlock.device.Repository.CredentialsRepository;
import ro.cheiafermecata.smartlock.device.Repository.DeviceRepository;

import java.io.InvalidObjectException;
import java.util.List;

@Component
public class RestDeviceRepository implements DeviceRepository {

    private static final String GET_URL = Urls.API + "/deviceDetails";

    private static final String NEW_URL = Urls.API + "/deviceDetails/new";

    private static final String SAVE_URL = Urls.API + "/deviceDetails/rename";

    private final RestTemplate restTemplate;

    private final CredentialsRepository credentialsRepository;

    private HttpHeaders headers = null;

    public RestDeviceRepository(RestTemplate restTemplate, CredentialsRepository credentialsRepository) {
        this.restTemplate = restTemplate;
        this.credentialsRepository = credentialsRepository;
    }

    private HttpHeaders getAuthHeader() {
        if (this.headers == null) {
            Credentials credentials = this.credentialsRepository.get();
            this.headers = RestHelper.getAuthHeader(credentials.getUsername(),credentials.getPassword());
        }
        return this.headers;
    }

    @Override
    public List<Device> getDevices() throws InvalidObjectException {
        List<Device> devices = restTemplate.exchange(
                RestDeviceRepository.GET_URL,
                HttpMethod.GET,
                new HttpEntity<>(this.getAuthHeader()),
                new ParameterizedTypeReference<List<Device>>() {
                }).getBody();
        if (devices == null) {
            throw new InvalidObjectException("No valid data was returned");
        }
        return devices;
    }

    @Override
    public Device newDevice(String deviceName) throws InvalidObjectException {
        HttpHeaders headers = this.getAuthHeader();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(RestDeviceRepository.NEW_URL)
                .queryParam("name", deviceName);
        Device device = restTemplate.exchange(
                builder.build().toString(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Device.class).getBody();
        if (device == null) {
            throw new InvalidObjectException("No valid data was returned");
        }
        return device;
    }

    @Override
    public Device updateDeviceName(Device device) throws InvalidObjectException {
        HttpHeaders headers = this.getAuthHeader();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(RestDeviceRepository.SAVE_URL)
                .queryParam("id", device.getId())
                .queryParam("name", device.getName());
        Device response = restTemplate.exchange(
                builder.build().toString(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Device.class).getBody();
        if (response == null) {
            throw new InvalidObjectException("No valid data was returned");
        }
        return response;
    }

}
