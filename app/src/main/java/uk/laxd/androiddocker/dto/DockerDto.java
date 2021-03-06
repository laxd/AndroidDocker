package uk.laxd.androiddocker.dto;

import android.databinding.BaseObservable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lawrence on 04/01/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class DockerDto extends BaseObservable {

    @JsonProperty("Id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
