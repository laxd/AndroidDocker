package uk.laxd.androiddocker;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.DrawerActions;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by lawrence on 13/02/17.
 */
public abstract class AbstractInstrumentedTestSetup {

    private static final String FILENAME_PREFIX = "/";

    private MockWebServer server = new MockWebServer();

    public String setupMockServer() throws IOException {
        server.start();

        return server.url("/").toString();
    }

    public void enqueueFiles(String... filenames) throws IOException {
        for(String filename : filenames) {
            filename = filename.startsWith(FILENAME_PREFIX) ? filename : FILENAME_PREFIX + filename;

            enqueue(IOUtils.toString(this.getClass().getResource(filename)));
        }
    }

    public void enqueue(String... messages) throws IOException {
        for(String message : messages) {
            server.enqueue(new MockResponse()
                    .setResponseCode(200)
                    .setBody(message));
        }
    }

    void navigate(String text, NavigationType navigationType) {
        switch (navigationType) {
            case MENU:
                openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
                break;
            case DRAWER:
                onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
                break;
            default:
                throw new RuntimeException("Invalid navigation type '" + navigationType + "'");
        }

        onView(withText(text)).perform(click());
    }

    public enum NavigationType {
        DRAWER,
        MENU;
    }

}
