package uk.laxd.androiddocker;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import rx.Observable;
import uk.laxd.androiddocker.dto.DockerContainer;
import uk.laxd.androiddocker.dto.DockerContainerDetail;
import uk.laxd.androiddocker.dto.DockerImage;
import uk.laxd.androiddocker.dto.DockerImageDetail;

/**
 * Created by lawrence on 04/01/17.
 */

public interface DockerService {

    @GET("/containers/json?all=1")
    Observable<List<DockerContainer>> getContainers();

    @GET("/containers/json")
    Observable<List<DockerContainer>> getContainers(@Query("all") boolean includeStopped);

    @GET("/images/json?dangling=false")
    Observable<List<DockerImage>> getImages();

    @GET("/images/json")
    Observable<List<DockerImage>> getImages(@Query("all") boolean all, @Query("dangling") boolean dangling);

    @GET("/images/{id}/json")
    Observable<DockerImageDetail> getImage(@Path("id") String id);

    @GET("/containers/{id}/json")
    Observable<DockerContainerDetail> getContainer(@Path("id") String id);

//    @Headers(value = "Accept: plain/text")
    @GET("/containers/{id}/logs?tail=10")
    @Streaming
    Observable<ResponseBody> getLogs(@Path("id") String id, @Query("stdout") boolean stdout, @Query("stderr") boolean stderr, @Query("since") Integer since);
}
