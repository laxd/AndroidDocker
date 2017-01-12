package uk.laxd.androiddocker;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;
import uk.laxd.androiddocker.dto.DockerContainer;
import uk.laxd.androiddocker.dto.DockerContainerDetail;
import uk.laxd.androiddocker.dto.DockerImage;
import uk.laxd.androiddocker.dto.DockerVersion;

/**
 * Created by lawrence on 04/01/17.
 */

public interface DockerService {

    @GET("/containers/json")
    Observable<List<DockerContainer>> getContainers();

    @GET("/images/json")
    Observable<List<DockerImage>> getImages();

    @GET("/containers/{id}/json")
    Observable<DockerContainerDetail> getContainer(@Path("id") String id);

    @GET("/version")
    Observable<DockerVersion> getVersion();
}
