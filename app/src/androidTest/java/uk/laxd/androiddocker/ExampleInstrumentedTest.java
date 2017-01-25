package uk.laxd.androiddocker;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

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
import uk.laxd.androiddocker.module.RetrofitModule;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.mockito.Mockito.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String CONTAINER_NAME = "testContainer";
    private static final String IMAGE_NAME = "testImage";
    private static final String DOCKER_ADDRESS = "http://testAddress:4243";


    private static final String MENU_ITEM_IMAGES = "Images";

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

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Mock DockerDao dockerDao;

    @Mock DockerServiceFactory dockerServiceFactory;

    @Mock DockerService dockerService;

    @Before
    public void setUp() throws Exception {
        when(dockerDao.getDockerAddress())
                .thenReturn(new DockerServer(DOCKER_ADDRESS));

        when(dockerServiceFactory.getDockerService())
                .thenReturn(dockerService);

        List<DockerContainer> dockerContainers = new ArrayList<>();
        dockerContainers.add(new DockerContainer(new String[]{CONTAINER_NAME},
                DockerContainer.ContainerState.RUNNING.toString(),
                IMAGE_NAME));

        when(dockerService.getContainers())
                .thenReturn(Observable.just(dockerContainers));

        when(dockerService.getContainer(anyString()))
                .thenReturn(Observable.just(new DockerContainerDetail(CONTAINER_NAME)));

        List<DockerImage> dockerImages = new ArrayList<>();
        dockerImages.add(new DockerImage(IMAGE_NAME));

        when(dockerService.getImages(anyBoolean(), anyBoolean()))
                .thenReturn(Observable.just(dockerImages));

    }

    @Test
    public void testContainersFragmentLoaded() throws Exception {
        activityTestRule.launchActivity(null);

        onView(withId(R.id.container_list)).check(matches(isDisplayed()));
    }

    @Test
    public void testImagesFragmentLoadedOnClick() throws Exception {
        activityTestRule.launchActivity(null);

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText(MENU_ITEM_IMAGES)).perform(click());

        onView(withId(R.id.image_list)).check(matches(isDisplayed()));
    }

    @Test
    public void testContainerFragmentLoadedOnClick() throws Exception {
        activityTestRule.launchActivity(null);

        onView(withText(CONTAINER_NAME)).perform(click());

        onView(withId(R.id.container_name)).check(matches(isDisplayed()));
    }
}
