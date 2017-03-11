package uk.laxd.androiddocker.domain;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lawrence on 11/01/17.
 */
@Getter
@Setter
public class DockerServer extends SugarRecord {

    private String address;

    public DockerServer() {
    }

    public DockerServer(String address) {
        this.address = address;
    }
}
