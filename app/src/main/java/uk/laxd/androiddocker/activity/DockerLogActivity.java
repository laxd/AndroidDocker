package uk.laxd.androiddocker.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import uk.laxd.androiddocker.fragment.DockerImageFragment;
import uk.laxd.androiddocker.fragment.DockerLogFragment;

/**
 * Created by lawrence on 28/03/17.
 */

public class DockerLogActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            Fragment fragment = new DockerLogFragment();

            fragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment)
                    .commit();
        }
    }
}
