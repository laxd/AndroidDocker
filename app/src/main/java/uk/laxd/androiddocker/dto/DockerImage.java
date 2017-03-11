package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lawrence on 04/01/17.
 */
@Getter
@Setter
public class DockerImage extends DockerDto {

    @JsonProperty("RepoTags")
    private String[] repoTags;
}
