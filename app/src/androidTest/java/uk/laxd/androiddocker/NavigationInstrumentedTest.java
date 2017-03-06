package uk.laxd.androiddocker;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import uk.laxd.androiddocker.activity.MainActivity;
import uk.laxd.androiddocker.dao.DockerDao;
import uk.laxd.androiddocker.domain.DockerServer;
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
public class NavigationInstrumentedTest extends AbstractInstrumentedTestSetup {

    private static final String SETUP_GUIDE = "Setup Guide";
    private static final String CONTAINER_NAME = "test_mysql";
    private static final String MENU_ITEM_IMAGES = "Images";

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
    public DockerDao dockerDao;

    @Before
    public void setUp() throws Exception {
        when(dockerDao.getDockerAddress())
                .thenReturn(new DockerServer(setupMockServer()));

        enqueueFiles("/containers.json");

        activityTestRule.launchActivity(null);
    }

    @Test
    public void testContainersFragmentLoaded() throws Exception {
        onView(withId(R.id.container_list)).check(matches(isDisplayed()));
    }

    @Test
    public void testImagesFragmentLoadedOnClick() throws Exception {
        enqueueFiles("images.json");

        navigate(MENU_ITEM_IMAGES, NavigationType.DRAWER);

        onView(withId(R.id.image_list)).check(matches(isDisplayed()));
    }

    @Test
    public void testContainerFragmentLoadedOnClick() throws Exception {
        enqueueFiles("container.json");

        onView(withText(CONTAINER_NAME)).perform(click());

        onView(withId(R.id.container_name)).check(matches(isDisplayed()));
    }

    @Test
    public void testSetupFragmentLoaded() throws Exception {
        navigate(SETUP_GUIDE, NavigationType.MENU);

        onView(withId(R.id.docker_address)).check(matches(isDisplayed()));
    }


}
