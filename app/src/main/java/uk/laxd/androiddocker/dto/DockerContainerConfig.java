package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lawrence on 11/03/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerContainerConfig {

    @JsonProperty("Image")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
