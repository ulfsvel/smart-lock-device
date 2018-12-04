package ro.cheiafermecata.smartlock.device.Repository;

import org.springframework.stereotype.Repository;
import ro.cheiafermecata.smartlock.device.Data.Credentials;

@Repository
public interface CredentialsRepository {

    /**
     * Gets the credentials
     * @return the persisted credentials
     */
    Credentials get();

    /**
     * Persists the credentials
     * @param credentials the credentials to persist
     */
    void save(Credentials credentials);

}
