package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by lawrence on 11/03/17.
 */
public class DockerImageDetail extends DockerDto {

    @JsonProperty("RepoTags")
    private String[] tags;

    @JsonProperty("VirtualSize")
    private BigInteger size;

    @JsonProperty("Created")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createdDate;

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public BigInteger getSize() {
        return size;
    }

    public void setSize(BigInteger size) {
        this.size = size;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
