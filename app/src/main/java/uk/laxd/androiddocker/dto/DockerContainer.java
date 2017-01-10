package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lawrence on 04/01/17.
 */
public class DockerContainer extends DockerDto {

    @JsonProperty("Names")
    private String[] names;

    @JsonProperty("State")
    private String state;

    @JsonProperty("Image")
    private String image;

    public String getName() {
        return names[0].replaceFirst("/", "");
    }

    public String getState() {
        return state;
    }

    public String getImage() {
        return image;
    }
}
