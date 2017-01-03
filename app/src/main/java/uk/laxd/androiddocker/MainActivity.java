package uk.laxd.androiddocker;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import uk.laxd.androiddocker.tasks.DockerPsRequestTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final TextView textView = (TextView) findViewById(R.id.text);

        Button button = (Button) findViewById(R.id.refresh_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DockerPsRequestTask dockerPsRequestTask = new DockerPsRequestTask(MainActivity.this, textView);

                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Loading docker PS data...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                dockerPsRequestTask.setProgressDialog(progressDialog);

                dockerPsRequestTask.execute();
            }
        });


    }
}