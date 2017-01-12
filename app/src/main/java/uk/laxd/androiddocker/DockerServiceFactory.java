package uk.laxd.androiddocker;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by lawrence on 11/01/17.
 */
public class DockerServiceFactory {

    private DockerService instance;

    public DockerService getDockerService() {
        return instance;
    }

    public DockerService createWithAddress(String address) {
        return new Retrofit.Builder()
                .baseUrl(address)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(DockerService.class);
    }

    public void updateDockerAddress(String address) {
        instance = createWithAddress(address);
    }

}
