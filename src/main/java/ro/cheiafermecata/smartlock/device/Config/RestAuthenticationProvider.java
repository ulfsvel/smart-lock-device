package ro.cheiafermecata.smartlock.device.Config;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ro.cheiafermecata.smartlock.device.Data.Credentials;
import ro.cheiafermecata.smartlock.device.Data.User;
import ro.cheiafermecata.smartlock.device.Repository.CredentialsRepository;
import ro.cheiafermecata.smartlock.device.Repository.Implementations.RestHelper;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestAuthenticationProvider implements AuthenticationProvider {

    private static final String LOGIN_URL = Urls.API + "/deviceDetails/auth";

    private final RestTemplate restTemplate;

    private final CredentialsRepository credentialsRepository;

    public RestAuthenticationProvider(RestTemplate restTemplate, CredentialsRepository credentialsRepository) {
        this.restTemplate = restTemplate;
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Credentials credentials = credentialsRepository.get();

        List<GrantedAuthority> grantList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        grantList.add(authority);

        HttpHeaders headers = RestHelper.getAuthHeader(username, password);
        try {
            restTemplate.exchange(
                    RestAuthenticationProvider.LOGIN_URL,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Boolean.class).getBody();
        } catch (Exception e) {
            if (username.equals(credentials.getUsername()) && password.equals(credentials.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password, grantList);
            }
            return null;
        }


        credentials.setUsername(username);
        credentials.setPassword(password);
        credentialsRepository.save(credentials);
        return new UsernamePasswordAuthenticationToken(username, password, grantList);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
