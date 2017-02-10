package uk.laxd.androiddocker;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by lawrence on 11/01/17.
 */
public class DockerServiceFactory {

    private static final int TIMEOUT_SECONDS = 10;

    // No need to check the HttpClient
    // every time a new address is given.
    private OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build();

    private DockerService instance;

    public DockerService getDockerService() {
        return instance;
    }

    public DockerService createWithAddress(String address) {
        return new Retrofit.Builder()
                .baseUrl(address)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build()
                .create(DockerService.class);
    }

    public void updateDockerAddress(String address) {
        Log.i("DockerServiceFactory", "Updating docker address to '" + address + "'");

        instance = createWithAddress(address);
    }

}
