package uk.laxd.androiddocker.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.DockerService;
import uk.laxd.androiddocker.DockerServiceFactory;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dto.DockerContainerDetail;

/**
 * Created by lawrence on 05/01/17.
 */

public class DockerContainerActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Inject
    protected DockerServiceFactory dockerServiceFactory;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.container_id)
    protected TextView id;

    @BindView(R.id.container_name)
    protected TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AndroidDockerApplication) getApplication()).getAndroidDockerComponent().inject(this);

        setContentView(R.layout.docker_container);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }

    @Override
    protected void onStart() {
        super.onStart();

        dockerServiceFactory.getDockerService()
                .getContainer(getIntent().getStringExtra("id"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DockerContainerDetail>() {
                    @Override
                    public void call(DockerContainerDetail dockerContainerDetail) {
                        id.setText(dockerContainerDetail.getId());
                        name.setText(dockerContainerDetail.getName());
                    }
                });
    }
}
