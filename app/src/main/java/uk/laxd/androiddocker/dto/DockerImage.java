package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by lawrence on 04/01/17.
 */
public class DockerImage extends DockerDto {

    @JsonProperty("RepoTags")
    private String[] repoTags;

    @JsonProperty("VirtualSize")
    private BigInteger size;

    public String[] getRepoTags() {
        return repoTags;
    }

    public void setRepoTags(String[] repoTags) {
        this.repoTags = repoTags;
    }

    public BigDecimal getSize() {
        return size == null ? new BigDecimal("0") : new BigDecimal(size);
    }

    public void setSize(BigInteger size) {
        this.size = size;
    }
}
