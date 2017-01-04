package uk.laxd.androiddocker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.tasks.DockerImagesRequestTask;

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

        containers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DockerContainersActivity.class);
                startActivity(intent);
            }
        });

        Button images = (Button) findViewById(R.id.show_images_button);

        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DockerImagesActivity.class);
                startActivity(intent);
            }
        });
    }
}