package uk.laxd.androiddocker;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.DrawerActions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by lawrence on 13/02/17.
 */
public class AbstractInstrumentedTestSetup {

    void navigate(String text, boolean isDrawer, boolean isMenuItem) {
        if(isDrawer) {
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        }
        else if(isMenuItem) {
            openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        }

        onView(withText(text)).perform(click());

    }

}
