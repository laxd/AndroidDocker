package uk.laxd.androiddocker.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.laxd.androiddocker.DockerServiceFactory;
import uk.laxd.androiddocker.DockerVersionServiceFactory;
import uk.laxd.androiddocker.dao.DockerDao;
import uk.laxd.androiddocker.domain.DockerServer;

/**
 * Created by lawrence on 05/01/17.
 */
@Module
public class RetrofitModule {

    @Provides
    @Singleton
    public DockerDao provideDockerDao() {
        return new DockerDao();
    }

    @Provides
    @Singleton
    public DockerServiceFactory provideDockerServiceFactory(DockerDao dockerDao) {
        DockerServiceFactory factory = new DockerServiceFactory();

        DockerServer dockerServer = dockerDao.getDockerAddress();

        if(dockerServer != null && dockerServer.getAddress() != null) {
            factory.updateDockerAddress(dockerServer.getAddress());
        }

        return factory;
    }

    @Provides
    @Singleton
    public DockerVersionServiceFactory provideDockerVersionServiceFactory() {
        return new DockerVersionServiceFactory();
    }
}
