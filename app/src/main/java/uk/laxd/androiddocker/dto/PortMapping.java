package uk.laxd.androiddocker.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lawrence on 26/01/17.
 */
public class PortMapping {

    private String source;

    private List<String> destinations = new ArrayList<>();

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<String> destinations) {
        this.destinations = destinations;
    }

    public String getDestinationsAsString() {
        StringBuilder sb = new StringBuilder();

        String separator = "";

        for(String destination : destinations) {
            sb.append(separator);
            sb.append(destination);

            separator = ", ";
        }

        return sb.toString();
    }
}
