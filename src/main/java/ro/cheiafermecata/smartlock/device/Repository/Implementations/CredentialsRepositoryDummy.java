package ro.cheiafermecata.smartlock.device.Repository.Implementations;

import org.springframework.stereotype.Component;
import ro.cheiafermecata.smartlock.device.Data.Credentials;
import ro.cheiafermecata.smartlock.device.Repository.CredentialsRepository;

@Component
public class CredentialsRepositoryDummy implements CredentialsRepository {

    private Credentials credentials = new Credentials("alexbotici@gmail.com",0L,"test");

    @Override
    public Credentials get() {
        return credentials;
    }

    @Override
    public void save(Credentials credentials) {
        this.credentials = credentials;
    }
}
