package ro.cheiafermecata.smartlock.device.Repository;

import org.springframework.stereotype.Repository;
import ro.cheiafermecata.smartlock.device.Data.Credentials;

@Repository
public interface CredentialsRepository {

    Credentials get();

    void save(Credentials credentials);

}
