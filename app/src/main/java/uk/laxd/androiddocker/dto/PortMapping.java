package uk.laxd.androiddocker.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lawrence on 26/01/17.
 */
@Getter
@Setter
public class PortMapping {

    private String source;

    private List<String> destinations = new ArrayList<>();
}
