package uk.laxd.androiddocker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.DockerService;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dto.DockerContainer;
import uk.laxd.androiddocker.dto.DockerContainerDetail;

/**
 * Created by lawrence on 05/01/17.
 */

public class DockerContainerActivity extends AppCompatActivity {

    @Inject
    protected DockerService dockerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AndroidDockerApplication) getApplication()).getAndroidDockerComponent().inject(this);

        setContentView(R.layout.docker_container);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView id = (TextView) findViewById(R.id.container_id);
        final TextView name = (TextView) findViewById(R.id.container_name);

        dockerService.getContainer(getIntent().getStringExtra("id"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DockerContainerDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(DockerContainerDetail dockerContainer) {
                        id.setText(dockerContainer.getId());
                        name.setText(dockerContainer.getName());
                    }
                });
    }
}
