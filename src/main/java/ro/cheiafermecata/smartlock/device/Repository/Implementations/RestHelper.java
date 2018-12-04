package ro.cheiafermecata.smartlock.device.Repository.Implementations;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

public class RestHelper {

    /**
     * Creates the auth type basic and accept json headers
     * @param username the username for the auth header
     * @param password the password for the auth header
     * @return the resulting HttpHeaders
     */
    public static HttpHeaders getAuthHeader(String username, String password) {

        String base64Credentials = new String(
                Base64.encodeBase64(
                        (
                                username + ":" + password
                        ).getBytes())
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return headers;
    }

}
