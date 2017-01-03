package uk.laxd.androiddocker.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by lawrence on 03/01/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerPsDto {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Names")
    private String[] names;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }
}
