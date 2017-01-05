package uk.laxd.androiddocker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;
import uk.laxd.androiddocker.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

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
}