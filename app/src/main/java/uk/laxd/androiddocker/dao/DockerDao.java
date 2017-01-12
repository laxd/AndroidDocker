package uk.laxd.androiddocker.dao;

import uk.laxd.androiddocker.domain.DockerServer;

/**
 * Created by lawrence on 06/01/17.
 */

public class DockerDao {

    public boolean requiresSetup() {
        return getDockerAddress() == null;
    }

    public void setDockerAddress(String dockerAddress) {
        DockerServer dockerServer = DockerServer.findById(DockerServer.class, 1L);

        if(dockerServer == null) {
            dockerServer = new DockerServer();
        }

        dockerServer.setAddress(dockerAddress);
        dockerServer.save();
    }

    public DockerServer getDockerAddress() {
        return DockerServer.findById(DockerServer.class, 1);
    }
}
