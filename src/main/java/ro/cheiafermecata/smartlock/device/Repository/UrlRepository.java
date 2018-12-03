package ro.cheiafermecata.smartlock.device.Repository;

import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository {

    String apiUrl();

    String wsUrl();

}
