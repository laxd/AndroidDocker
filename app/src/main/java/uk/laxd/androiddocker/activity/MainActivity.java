package uk.laxd.androiddocker.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.design.widget.TabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.DockerVersionServiceFactory;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dao.DockerDao;
import uk.laxd.androiddocker.fragment.DockerContainersFragment;
import uk.laxd.androiddocker.fragment.DockerImagesFragment;
import uk.laxd.androiddocker.fragment.SetupFragment;

public class MainActivity extends AppCompatActivity {

    static final int SETUP_REQUEST = 1;

    @Inject
    protected DockerDao dockerDao;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.viewpager)
    protected ViewPager viewPager;

    @BindView(R.id.tabs)
    protected TabLayout tabs;

    @BindView(R.id.nav_view)
    protected NavigationView navigationView;

    @BindView(R.id.drawer)
    protected DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((AndroidDockerApplication) getApplication())
                .getAndroidDockerComponent()
                .inject(this);

        if(dockerDao.requiresSetup()) {
            // Redirect to setup
            Intent intent = new Intent(this, SetupActivity.class);

            startActivity(intent);
        }

        setSupportActionBar(toolbar);

        Adapter adapter = new Adapter(getSupportFragmentManager());

        adapter.addFragment(new DockerContainersFragment(), "Containers");
        adapter.addFragment(new DockerImagesFragment(), "Images");

        viewPager.setAdapter(adapter);

        for(String tab : adapter.fragmentTitles) {
            tabs.addTab(tabs.newTab().setText(tab));
        }

        tabs.setupWithViewPager(viewPager);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_view);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);

                        switch (menuItem.getItemId()) {
                            case R.id.setup_guide:
                                Intent intent = new Intent(MainActivity.this, SetupActivity.class);

                                startActivityForResult(intent, SETUP_REQUEST);
                        }

                        // Closing drawer on item click
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                Intent intent = new Intent(this, SetupActivity.class);

                startActivityForResult(intent, SETUP_REQUEST);

                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SETUP_REQUEST:
                if(resultCode == RESULT_OK) {
                    // Refresh data
                }
                else {
                    //Do nothing
                }
        }
    }

    public class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> fragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }
}