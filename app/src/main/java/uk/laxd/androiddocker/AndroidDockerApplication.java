package uk.laxd.androiddocker;

import com.orm.SugarApp;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import timber.log.Timber;
import uk.laxd.androiddocker.dao.DockerDao;
import uk.laxd.androiddocker.domain.DockerServer;

/**
 * Created by lawrence on 05/01/17.
 */

public class AndroidDockerApplication extends SugarApp {

    @Inject
    protected DockerDao dockerDao;

    @Getter
    private String dockerAddress;

    @Getter
    @Setter
    private AndroidDockerComponent androidDockerComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        androidDockerComponent = DaggerAndroidDockerComponent.create();

        androidDockerComponent.inject(this);

        DockerServer dockerServer = dockerDao.getDockerAddress();

        if(dockerServer != null) {
            dockerAddress = dockerServer.getAddress();
        }

    }

    public void updateDockerAddress(String dockerAddress) {
        dockerDao.setDockerAddress(dockerAddress);

        this.dockerAddress = dockerAddress;
    }
}
