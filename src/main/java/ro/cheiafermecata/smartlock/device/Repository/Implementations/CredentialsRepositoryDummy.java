package ro.cheiafermecata.smartlock.device.Repository.Implementations;

import org.springframework.stereotype.Component;
import ro.cheiafermecata.smartlock.device.Data.Credentials;
import ro.cheiafermecata.smartlock.device.Repository.CredentialsRepository;

@Component
public class CredentialsRepositoryDummy implements CredentialsRepository {

    @Override
    public Credentials get() {
        return new Credentials("alexbotici@gmail.com","3","test");
    }

    @Override
    public void save() {

    }
}
