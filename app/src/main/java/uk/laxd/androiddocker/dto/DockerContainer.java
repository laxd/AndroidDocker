package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lawrence on 04/01/17.
 */
public class DockerContainer extends DockerDto {

    @JsonProperty("Names")
    private String[] names;

    public String[] getNames() {
        return names;
    }
}
