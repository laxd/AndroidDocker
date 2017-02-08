package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lawrence on 26/01/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mount {

    @JsonProperty("Source")
    private String source;

    @JsonProperty("Destination")
    private String destination;

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }
}
