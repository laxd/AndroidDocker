package uk.laxd.androiddocker.service;

import android.content.Context;

import java.util.Observable;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import uk.laxd.androiddocker.DockerService;
import uk.laxd.androiddocker.dao.DockerDao;

/**
 * Created by lawrence on 06/01/17.
 */
public class DockerAddressServiceImpl implements DockerAddressService {

    private DockerDao dockerDao;

    public DockerAddressServiceImpl(Context context) {
        dockerDao = DockerDao.getInstance(context);
    }

    @Override
    public boolean isSetup() {
        return dockerDao.isSetup();
    }

    @Override
    public boolean setupIfValid(String address) {
        if(isValid(address)) {
            setup(address);

            return true;
        }

        return false;
    }

    @Override
    public boolean isValid(String address) {
        DockerService dockerService = new Retrofit.Builder()
                .baseUrl(address)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(DockerService.class);

        return (dockerService.getVersion() != null);
    }

    @Override
    public void setup(String address) {
        dockerDao.setDockerAddress(address);
    }
}
