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
import uk.laxd.androiddocker.adapter.DockerContainerListAdapter;
import uk.laxd.androiddocker.dto.DockerContainer;

/**
 * Created by lawrence on 04/01/17.
 */
public class DockerContainersActivity extends ListActivity {

    @Inject
    protected DockerService dockerService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AndroidDockerApplication) getApplication()).getAndroidDockerComponent()
                .inject(this);

        setContentView(R.layout.docker_containers);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final ArrayAdapter<DockerContainer> dockerContainerAdapter = new DockerContainerListAdapter(this, R.layout.docker_container_list_row, new ArrayList<DockerContainer>());
        setListAdapter(dockerContainerAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dockerService.getContainers()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<DockerContainer>>() {
                            @Override
                            public void onCompleted() {
                                swipeRefreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public void onNext(List<DockerContainer> dockerContainers) {
                                dockerContainerAdapter.clear();
                                dockerContainerAdapter.addAll(dockerContainers);
                            }
                        });
            }
        });
    }

}
