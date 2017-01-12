package uk.laxd.androiddocker.service;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import uk.laxd.androiddocker.DockerService;
import uk.laxd.androiddocker.DockerServiceFactory;
import uk.laxd.androiddocker.dao.DockerDao;
import uk.laxd.androiddocker.domain.DockerServer;

/**
 * Created by lawrence on 06/01/17.
 */
public class DockerAddressServiceImpl implements DockerAddressService {

    @Inject
    protected DockerDao dockerDao;

    @Inject
    protected DockerServiceFactory dockerServiceFactory;

    @Override
    public boolean isSetup() {
        return dockerDao.requiresSetup();
    }

    @Override
    public boolean setupIfValid(String address) {
        boolean wasSetup = false;

        if(isValid(address)) {
            setup(address);
            wasSetup = true;
        }

        return wasSetup;
    }

    @Override
    public boolean isValid(String address) {
        return (dockerServiceFactory.createWithAddress(address)
                .getVersion() != null);
    }

    @Override
    public void setup(String address) {
        dockerDao.setDockerAddress(address);
    }
}
