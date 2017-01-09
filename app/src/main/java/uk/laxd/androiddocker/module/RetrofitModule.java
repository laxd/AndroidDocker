package uk.laxd.androiddocker.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import uk.laxd.androiddocker.DockerService;
import uk.laxd.androiddocker.dao.DockerDao;

/**
 * Created by lawrence on 05/01/17.
 */
@Module
public class RetrofitModule {

    private Context context;

    public RetrofitModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        // TODO: Add context? Implement getDockerAddress
        String address = DockerDao.getInstance(context).getDockerAddress();

        return provideRetrofitWithAddress(address);
    }

    public Retrofit provideRetrofitWithAddress(String address) {
        return new Retrofit.Builder()
                .baseUrl(address)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public DockerService provideDockerService(Retrofit retrofit) {
        return retrofit.create(DockerService.class);
    }

}
