package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lawrence on 11/03/17.
 */
@Getter
@Setter
public class DockerImageDetail extends DockerDto {

    @JsonProperty("RepoTags")
    private String[] tags;

    @JsonProperty("VirtualSize")
    private BigInteger size;

    @JsonProperty("Created")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createdDate;

}
