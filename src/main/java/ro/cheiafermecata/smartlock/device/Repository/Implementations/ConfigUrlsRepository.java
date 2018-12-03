package ro.cheiafermecata.smartlock.device.Repository.Implementations;

import ro.cheiafermecata.smartlock.device.Repository.UrlRepository;

import javax.validation.constraints.NotBlank;


public class ConfigUrlsRepository implements UrlRepository {

    @NotBlank
    private String apiUrl;

    @NotBlank
    private String wsUrl;

    public ConfigUrlsRepository(@NotBlank String apiUrl, @NotBlank String wsUrl) {
        this.apiUrl = apiUrl;
        this.wsUrl = wsUrl;
    }

    @Override
    public String apiUrl() {
        return this.apiUrl;
    }

    @Override
    public String wsUrl() {
        return this.wsUrl;
    }
}
