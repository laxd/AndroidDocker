package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lawrence on 04/01/17.
 */
public class DockerImage extends DockerDto {

    @JsonProperty("RepoTags")
    private String[] repoTags;

    public String[] getRepoTags() {
        return repoTags;
    }
}
