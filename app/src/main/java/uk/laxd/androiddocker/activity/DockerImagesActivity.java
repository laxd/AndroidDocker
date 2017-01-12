package uk.laxd.androiddocker.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.DockerService;
import uk.laxd.androiddocker.DockerServiceFactory;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.adapter.DockerImagesListAdapter;
import uk.laxd.androiddocker.dto.DockerImage;

/**
 * Created by lawrence on 04/01/17.
 */

public class DockerImagesActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Unbinder unbinder;

    @Inject
    protected DockerServiceFactory dockerServiceFactory;

    private DockerService dockerService;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.image_list)
    protected ListView listView;

    @BindView(R.id.refresh_layout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    private ArrayAdapter<DockerImage> dockerImageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AndroidDockerApplication) getApplication())
                .getAndroidDockerComponent()
                .inject(this);

        setContentView(R.layout.docker_images);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        dockerService = dockerServiceFactory.getDockerService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onStart() {
        super.onStart();

        dockerImageAdapter = new DockerImagesListAdapter(this, R.layout.docker_image_list_row, new ArrayList<DockerImage>());
        listView.setAdapter(dockerImageAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);


        onRefresh();
    }

    @Override
    public void onRefresh() {
        dockerService.getImages()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DockerImage>>() {
                    @Override
                    public void onCompleted() {
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(List<DockerImage> dockerContainers) {
                        dockerImageAdapter.clear();
                        dockerImageAdapter.addAll(dockerContainers);
                    }
                });
    }
}
