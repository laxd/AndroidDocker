package uk.laxd.androiddocker;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import rx.Observable;
import uk.laxd.androiddocker.activity.MainActivity;
import uk.laxd.androiddocker.dao.DockerDao;
import uk.laxd.androiddocker.domain.DockerServer;
import uk.laxd.androiddocker.dto.DockerContainer;
import uk.laxd.androiddocker.dto.DockerContainerDetail;
import uk.laxd.androiddocker.dto.DockerImage;
import uk.laxd.androiddocker.dto.DockerVersion;
import uk.laxd.androiddocker.dto.NetworkSettings;
import uk.laxd.androiddocker.module.RetrofitModule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class SetupInstrumentedTest extends AbstractInstrumentedTestSetup {

    private static final String SETUP_GUIDE = "Setup Guide";
    private static final String CONTAINER_NAME = "testContainer";
    private static final String IMAGE_NAME = "testImage";
    private static final String DOCKER_VERSION = "1.2.3";
    private static final String DOCKER_ADDRESS = "http://test.com:4243";
    private static final String INVALID_ADDRESS = "invalidurl.com";


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class, true, false);


    @Rule
    public DaggerMockRule<AndroidDockerComponent> daggerMockRule = new DaggerMockRule<>(AndroidDockerComponent.class, new RetrofitModule())
            .set(new DaggerMockRule.ComponentSetter<AndroidDockerComponent>() {
                @Override
                public void setComponent(AndroidDockerComponent component) {
                    AndroidDockerApplication application = (AndroidDockerApplication) InstrumentationRegistry.getInstrumentation()
                            .getTargetContext().getApplicationContext();

                    application.setAndroidDockerComponent(component);
                }
            });

    @Mock
    DockerDao dockerDao;
    @Mock
    DockerServiceFactory dockerServiceFactory;
    @Mock
    DockerService dockerService;
    @Mock
    DockerVersion dockerVersion;

    @Before
    public void setUp() throws Exception {
        when(dockerService.getVersion())
                .thenReturn(Observable.just(dockerVersion));

        when(dockerVersion.getVersion())
                .thenReturn(DOCKER_VERSION);


        when(dockerDao.getDockerAddress())
                .thenReturn(new DockerServer(DOCKER_ADDRESS));

        when(dockerServiceFactory.getDockerService())
                .thenReturn(dockerService);

        when(dockerServiceFactory.createWithAddress(DOCKER_ADDRESS))
                .thenReturn(dockerService);

        List<DockerContainer> dockerContainers = new ArrayList<>();
        dockerContainers.add(new DockerContainer(new String[]{CONTAINER_NAME},
                DockerContainer.ContainerState.RUNNING,
                IMAGE_NAME));

        when(dockerService.getContainers())
                .thenReturn(Observable.just(dockerContainers));

        DockerContainerDetail dockerContainerDetail = new DockerContainerDetail(CONTAINER_NAME);
        dockerContainerDetail.setNetworkSettings(new NetworkSettings());

        when(dockerService.getContainer(anyString()))
                .thenReturn(Observable.just(new DockerContainerDetail(CONTAINER_NAME)));

        List<DockerImage> dockerImages = new ArrayList<>();
        dockerImages.add(new DockerImage(IMAGE_NAME));

        when(dockerService.getImages(anyBoolean(), anyBoolean()))
                .thenReturn(Observable.just(dockerImages));

        activityTestRule.launchActivity(null);

        navigate(SETUP_GUIDE, false, true);
    }

    @Test
    public void testDockerAddressUpdatedInDockerService() throws Exception {
        onView(withId(R.id.docker_address)).perform(typeText(DOCKER_ADDRESS));

        onView(withId(R.id.submit)).perform(click());

        verify(dockerServiceFactory).updateDockerAddress(DOCKER_ADDRESS);
    }

    @Test
    public void testDockerContaiersFragmentLoadedOnSuccessfulAddress() throws Exception {
        onView(withId(R.id.docker_address)).perform(typeText(DOCKER_ADDRESS));

        onView(withId(R.id.submit)).perform(click());

        onView(withId(R.id.container_list)).check(matches(isDisplayed()));
    }

    @Test
    public void testDockerAddressNotUpdatedForInvalidURL() throws Exception {
        when(dockerService.getVersion())
                .thenReturn(Observable.<DockerVersion>error(new MalformedURLException()));

        onView(withId(R.id.docker_address)).perform(typeText(INVALID_ADDRESS));

        onView(withId(R.id.submit)).perform(click());

        verify(dockerServiceFactory, never()).updateDockerAddress(DOCKER_ADDRESS);
    }
}
