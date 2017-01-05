package uk.laxd.androiddocker.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import uk.laxd.androiddocker.DockerService;

/**
 * Created by lawrence on 05/01/17.
 */
@Module
public class RetrofitModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://192.168.2.36:4243")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    DockerService provideDockerService(Retrofit retrofit) {
        return retrofit.create(DockerService.class);
    }

}
