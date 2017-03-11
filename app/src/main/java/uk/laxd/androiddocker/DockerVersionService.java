package uk.laxd.androiddocker;

import retrofit2.http.GET;
import rx.Observable;
import uk.laxd.androiddocker.dto.DockerVersion;

/**
 * Created by lawrence on 09/03/17.
 */

public interface DockerVersionService {

    @GET("/version")
    Observable<DockerVersion> getVersion();
}
