package uk.laxd.androiddocker;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by lawrence on 09/03/17.
 */

public class DockerVersionServiceFactory {
    private static final int TIMEOUT_SECONDS = 3;

    // No need to check the HttpClient
    // every time a new address is given.
    private OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build();

    public DockerVersionService getForAddress(String address) {
        return new Retrofit.Builder()
                .baseUrl(address)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build()
                .create(DockerVersionService.class);
    }
}
