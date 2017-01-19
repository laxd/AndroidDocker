package uk.laxd.androiddocker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dao.DockerDao;
import uk.laxd.androiddocker.fragment.DockerContainersFragment;
import uk.laxd.androiddocker.fragment.DockerImagesFragment;
import uk.laxd.androiddocker.fragment.SetupFragment;

public class MainActivity extends AppCompatActivity {

    @Inject
    protected DockerDao dockerDao;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;

    @BindView(R.id.left_drawer)
    protected ListView navList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((AndroidDockerApplication) getApplication())
                .getAndroidDockerComponent()
                .inject(this);

    }

    @OnItemClick(R.id.left_drawer)
    protected void clickDrawerItem(int pos) {
        Fragment fragment;

        switch (pos) {
            case 0:
                fragment = new DockerContainersFragment();
                break;
            case 1:
                fragment = new DockerImagesFragment();
                break;
            case 2:
                fragment = new SetupFragment();
                break;
            default:
                throw new IllegalStateException("Invalid drawer position: '" + pos + "'");
        }

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.content_frame, fragment);
        tx.commit();

        drawer.closeDrawer(navList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Fragment fragment;

        if(dockerDao.requiresSetup()) {
            Log.i(MainActivity.class.toString(), "Docker address not setup yet, redirecting to SetupFragment");

            fragment = new SetupFragment();
        }
        else {
            Log.i(MainActivity.class.toString(), "Using " + dockerDao.getDockerAddress().getAddress() + " as address.");

            fragment = new DockerContainersFragment();
        }

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.content_frame, fragment);
        tx.commit();

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.main_activity_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setup_guide:
                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.content_frame, new SetupFragment());
                tx.commit();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}