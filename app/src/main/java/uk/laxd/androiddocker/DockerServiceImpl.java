package uk.laxd.androiddocker;

import java.util.List;

import uk.laxd.androiddocker.dto.DockerContainer;
import uk.laxd.androiddocker.dto.DockerImage;

/**
 * Created by lawrence on 04/01/17.
 */
public class DockerServiceImpl extends AbstractDockerService {
    @Override
    public List<DockerContainer> getContainers() {
        return getObjects("/containers/json", DockerContainer[].class);
    }

    @Override
    public List<DockerImage> getImages() {
        return getObjects("/images/json", DockerImage[].class);
    }
}
