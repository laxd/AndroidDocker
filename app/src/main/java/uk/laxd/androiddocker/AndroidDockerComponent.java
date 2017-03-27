package uk.laxd.androiddocker;

import javax.inject.Singleton;

import dagger.Component;
import uk.laxd.androiddocker.activity.SetupActivity;
import uk.laxd.androiddocker.fragment.DockerContainerFragment;
import uk.laxd.androiddocker.fragment.DockerContainersFragment;
import uk.laxd.androiddocker.fragment.DockerImageFragment;
import uk.laxd.androiddocker.fragment.DockerImagesFragment;
import uk.laxd.androiddocker.activity.MainActivity;
import uk.laxd.androiddocker.fragment.SetupFragment;
import uk.laxd.androiddocker.module.RetrofitModule;

/**
 * Created by lawrence on 05/01/17.
 */
@Singleton
@Component(modules = {RetrofitModule.class})
public interface AndroidDockerComponent {
    void inject(MainActivity dockerImagesActivity);
    void inject(SetupActivity setupActivity);

    void inject(DockerImagesFragment dockerImagesFragment);
    void inject(DockerContainersFragment dockerContainersActivity);
    void inject(DockerContainerFragment dockerContainerFragment);
    void inject(SetupFragment setupFragment);
    void inject(DockerImageFragment dockerImageFragment);
    void inject(AndroidDockerApplication androidDockerApplication);
}
