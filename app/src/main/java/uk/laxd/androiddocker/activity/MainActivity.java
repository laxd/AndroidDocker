package uk.laxd.androiddocker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dao.DockerDao;

public class MainActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Inject
    protected DockerDao dockerDao;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.navList)
    protected ListView navList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AndroidDockerApplication) getApplication())
                .getAndroidDockerComponent()
                .inject(this);

        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        navList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"Item1", "Item2"}));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.show_containers_button)
    protected void onClickContainersButton() {
        Intent intent = new Intent(MainActivity.this, DockerContainersActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.show_images_button)
    protected void onClickImagesButton() {
        Intent intent = new Intent(MainActivity.this, DockerImagesActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(dockerDao.requiresSetup()) {
            Log.i(MainActivity.class.toString(), "Docker address not setup yet, redirecting to SetupActivity");

            Intent intent = new Intent(this, SetupActivity.class);
            startActivity(intent);

            return;
        }

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
                Intent intent = new Intent(this, SetupActivity.class);
                startActivity(intent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}