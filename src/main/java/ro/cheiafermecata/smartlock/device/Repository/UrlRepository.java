package ro.cheiafermecata.smartlock.device.Repository;

import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository {

    /**
     * Return the url for the REST API
     * @return the url of the REST API
     */
    String apiUrl();

    /**
     * Return the url for the REST API
     * @return the url of the REST API
     */
    String wsUrl();

}
