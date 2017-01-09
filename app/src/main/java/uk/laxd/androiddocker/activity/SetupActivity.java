package uk.laxd.androiddocker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dao.DockerDao;

/**
 * Created by lawrence on 06/01/17.
 */

public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setup);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final EditText editText = (EditText) findViewById(R.id.docker_address);

        Button button = (Button) findViewById(R.id.submit);

        RxView.clicks(button)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        // TODO: Check address is valid first
                        String address = editText.getText().toString();

                        Log.d(SetupActivity.class.toString(), "Setting up docker address as " + address);

                        DockerDao dockerDao = DockerDao.getInstance(getApplicationContext());

                        dockerDao.setDockerAddress(address);

                        Intent intent = new Intent(SetupActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
    }
}
