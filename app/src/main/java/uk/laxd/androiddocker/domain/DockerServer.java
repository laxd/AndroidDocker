package uk.laxd.androiddocker.domain;

import com.orm.SugarRecord;

/**
 * Created by lawrence on 11/01/17.
 */
public class DockerServer extends SugarRecord {

    private String address;

    public DockerServer() {
    }

    public DockerServer(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
