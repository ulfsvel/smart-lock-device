package ro.cheiafermecata.smartlock.device.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ro.cheiafermecata.smartlock.device.Repository.Implementations.ConfigUrlsRepository;
import ro.cheiafermecata.smartlock.device.Repository.UrlRepository;

import javax.validation.constraints.NotBlank;

@Configuration
@ConfigurationProperties("urls")
public class MiscConfig {

    @NotBlank
    private String api;

    @NotBlank
    private String ws;

    public void setApi(String api) {
        this.api = api;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public UrlRepository urlRepository() {
        return new ConfigUrlsRepository(api,ws);
    }

}
