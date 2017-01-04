package uk.laxd.androiddocker;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import uk.laxd.androiddocker.dto.DockerDto;
import uk.laxd.androiddocker.tasks.DockerPsDto;

/**
 * Created by lawrence on 04/01/17.
 */
public abstract class AbstractDockerService implements DockerService {

    private String baseUrl = "http://10.10.10.142:4243";

    protected <T extends DockerDto> T getObject(String urlPath, Class<T> type) {
        String url = baseUrl + urlPath;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        return restTemplate.getForObject(url, type);
    }

    protected <T extends DockerDto> List<T> getObjects(String urlPath, Class<T[]> type) {
        String url = baseUrl + urlPath;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        return Arrays.asList(restTemplate.getForObject(url, type));
    }
}
