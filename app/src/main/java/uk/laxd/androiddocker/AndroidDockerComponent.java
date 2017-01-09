package uk.laxd.androiddocker;

import javax.inject.Singleton;

import dagger.Component;
import uk.laxd.androiddocker.activity.DockerContainerActivity;
import uk.laxd.androiddocker.activity.DockerContainersActivity;
import uk.laxd.androiddocker.activity.DockerImagesActivity;
import uk.laxd.androiddocker.module.RetrofitModule;

/**
 * Created by lawrence on 05/01/17.
 */
@Singleton
@Component(modules = {RetrofitModule.class})
public interface AndroidDockerComponent {
    void inject(DockerImagesActivity dockerImagesActivity);
    void inject(DockerContainersActivity dockerContainersActivity);
    void inject(DockerContainerActivity dockerContainerActivity);
}
