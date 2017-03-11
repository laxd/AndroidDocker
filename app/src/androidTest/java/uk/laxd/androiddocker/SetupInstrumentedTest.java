package uk.laxd.androiddocker;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.io.IOException;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import retrofit2.HttpException;
import rx.Observable;
import uk.laxd.androiddocker.activity.MainActivity;
import uk.laxd.androiddocker.dao.DockerDao;
import uk.laxd.androiddocker.domain.DockerServer;
import uk.laxd.androiddocker.dto.DockerVersion;
import uk.laxd.androiddocker.module.RetrofitModule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class SetupInstrumentedTest extends AbstractInstrumentedTestSetup {

    private static final String SETUP_GUIDE = "Setup Guide";
    private static final String VALID_ADDRESS = "http://test.com:4243";
    private static final String INVALID_ADDRESS = "invalidurl.com";

    @Mock DockerServiceFactory dockerServiceFactory = new DockerServiceFactory();

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

    @Mock DockerDao dockerDao;

    @Mock DockerVersionServiceFactory dockerVersionServiceFactory;
    @Mock DockerVersionService dockerVersionService;
    @Mock DockerVersionService invalidDockerVersionService;
    @Before
    public void setUp() throws Exception {
        when(dockerVersionServiceFactory.getForAddress(INVALID_ADDRESS))
                .thenReturn(invalidDockerVersionService);
        when(dockerVersionServiceFactory.getForAddress(VALID_ADDRESS))
                .thenReturn(dockerVersionService);
        when(dockerVersionService.getVersion())
                .thenReturn(Observable.just(new DockerVersion()));

        doReturn(Observable.<DockerVersion>error(new IOException("Failed to connect")))
                .when(invalidDockerVersionService).getVersion();

        String address = setupMockServer();

        // Todo: Replaced mocked dockerServiceFactory with a spy
        // to allow creating dockerService naturally.
        // Failing to spy for some reason...
        DockerService dockerService = new DockerServiceFactory().createWithAddress(address);
        when(dockerServiceFactory.getDockerService())
                .thenReturn(dockerService);

        when(dockerDao.getDockerAddress())
                .thenReturn(new DockerServer(address));

        enqueueFiles("containers.json");

        activityTestRule.launchActivity(null);

        navigate(SETUP_GUIDE, NavigationType.MENU);
    }

    @Test
    public void testDockerAddressUpdatedInDockerService() throws Exception {
        enqueueFiles("containers.json");

        onView(withId(R.id.docker_address)).perform(typeText(VALID_ADDRESS));

        onView(withId(R.id.submit)).perform(click());

        verify(dockerDao).setDockerAddress(VALID_ADDRESS);
    }

    @Test
    public void testDockerContaiersFragmentLoadedOnSuccessfulAddress() throws Exception {
        enqueueFiles("containers.json");

        onView(withId(R.id.docker_address)).perform(typeText(VALID_ADDRESS));

        onView(withId(R.id.submit)).perform(click());

        onView(withId(R.id.container_list)).check(matches(isDisplayed()));
    }

    @Test
    public void testDockerAddressNotUpdatedForInvalidURL() throws Exception {
        onView(withId(R.id.docker_address)).perform(typeText(INVALID_ADDRESS));

        onView(withId(R.id.submit)).perform(click());

        verify(dockerServiceFactory, never()).updateDockerAddress(INVALID_ADDRESS);
    }
}
