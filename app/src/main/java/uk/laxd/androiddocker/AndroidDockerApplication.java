package uk.laxd.androiddocker;

import android.app.Application;

import uk.laxd.androiddocker.module.RetrofitModule;

/**
 * Created by lawrence on 05/01/17.
 */

public class AndroidDockerApplication extends Application {

    private AndroidDockerComponent androidDockerComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        androidDockerComponent = DaggerAndroidDockerComponent.create();
    }

    public AndroidDockerComponent getAndroidDockerComponent() {
        return androidDockerComponent;
    }
}
