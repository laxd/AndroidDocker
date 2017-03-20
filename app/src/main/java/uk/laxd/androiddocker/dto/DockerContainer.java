package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lawrence on 04/01/17.
 */
public class DockerContainer extends DockerDto {

    @JsonProperty("Names")
    private String[] names = new String[0];

    @JsonProperty("State")
    private DockerContainerState state;

    @JsonProperty("Image")
    private String image;

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public DockerContainerState getState() {
        return state;
    }

    public void setState(DockerContainerState state) {
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
