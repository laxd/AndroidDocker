package uk.laxd.androiddocker.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.DockerService;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.adapter.DockerImagesListAdapter;
import uk.laxd.androiddocker.dto.DockerImage;

/**
 * Created by lawrence on 04/01/17.
 */

public class DockerImagesActivity extends ListActivity {

    @Inject
    protected DockerService dockerService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AndroidDockerApplication) getApplication()).getAndroidDockerComponent()
                .inject(this);

        setContentView(R.layout.docker_images);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final ArrayAdapter<DockerImage> dockerImageAdapter = new DockerImagesListAdapter(this, R.layout.docker_image_list_row, new ArrayList<DockerImage>());
        setListAdapter(dockerImageAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        });

    }
}
