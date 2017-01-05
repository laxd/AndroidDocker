package uk.laxd.androiddocker;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;
import uk.laxd.androiddocker.dto.DockerContainer;
import uk.laxd.androiddocker.dto.DockerImage;

/**
 * Created by lawrence on 04/01/17.
 */

public interface DockerService {

    @GET("/containers/json")
    Observable<List<DockerContainer>> getContainers();

    @GET("/images/json")
    Observable<List<DockerImage>> getImages();

}
