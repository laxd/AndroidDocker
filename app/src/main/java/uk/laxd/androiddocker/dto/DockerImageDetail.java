package uk.laxd.androiddocker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import uk.laxd.androiddocker.BR;
import uk.laxd.androiddocker.R;

/**
 * Created by lawrence on 11/03/17.
 */
public class DockerImageDetail extends DockerDto {

    @JsonProperty("RepoTags")
    private List<String> tags;

    @JsonProperty("VirtualSize")
    private BigInteger size;

    @JsonProperty("Created")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createdDate;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public BigDecimal getSize() {
        return size == null ? new BigDecimal("0") : new BigDecimal(size);
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

    public ItemBinding<String> tagsBinding = ItemBinding.of(BR.tag, R.layout.docker_image_tag);
}
