package uk.laxd.androiddocker.dto;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lawrence on 26/01/17.
 */
public class PortMapping {

    private String source;

    private final List<String> destinations = new ArrayList<>();

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public String getDestination() {
        return TextUtils.join(", ", destinations);
    }
}
