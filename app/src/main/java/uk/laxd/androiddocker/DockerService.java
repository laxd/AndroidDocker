package uk.laxd.androiddocker;

import java.util.List;

import uk.laxd.androiddocker.dto.DockerContainer;
import uk.laxd.androiddocker.dto.DockerImage;

/**
 * Created by lawrence on 04/01/17.
 */

public interface DockerService {

    public List<DockerContainer> getContainers();
    public List<DockerImage> getImages();

}
