package uk.laxd.androiddocker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dao.DockerDao;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        DockerDao dockerDao = DockerDao.getInstance(getApplicationContext());

        if(!dockerDao.isSetup()) {
            Log.i(MainActivity.class.toString(), "Docker address not setup yet, redirecting to SetupActivity");

            Intent intent = new Intent(this, SetupActivity.class);
            startActivity(intent);

            return;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button containers = (Button) findViewById(R.id.show_containers_button);

        RxView.clicks(containers)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(MainActivity.this, DockerContainersActivity.class);
                        startActivity(intent);
                    }
                });

        Button images = (Button) findViewById(R.id.show_images_button);

        RxView.clicks(images)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(MainActivity.this, DockerImagesActivity.class);
                        startActivity(intent);
                    }
                });
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