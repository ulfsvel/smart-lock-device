package ro.cheiafermecata.smartlock.device.Repository;

import org.springframework.stereotype.Component;
import ro.cheiafermecata.smartlock.device.Data.Credentials;

@Component
public interface CredentialsRepository {

    Credentials get();

    void save(Credentials credentials);

}
