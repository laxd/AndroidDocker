package uk.laxd.androiddocker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.laxd.androiddocker.AndroidDockerApplication;
import uk.laxd.androiddocker.DockerService;
import uk.laxd.androiddocker.DockerServiceFactory;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.adapter.DockerContainerListAdapter;
import uk.laxd.androiddocker.dto.DockerContainer;

/**
 * Created by lawrence on 04/01/17.
 */
public class DockerContainersActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Unbinder unbinder;

    private DockerContainerListAdapter dockerContainerAdapter;

    @BindView(R.id.refresh_layout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.container_list)
    protected ListView listView;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Inject
    protected DockerServiceFactory dockerServiceFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AndroidDockerApplication) getApplication())
                .getAndroidDockerComponent()
                .inject(this);

        setContentView(R.layout.docker_containers);

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

        dockerContainerAdapter = new DockerContainerListAdapter(this, R.layout.docker_container_list_row, new ArrayList<DockerContainer>());
        listView.setAdapter(dockerContainerAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @OnItemClick(R.id.container_list)
    public void onContainerClick(int position) {
        DockerContainer dockerContainer = dockerContainerAdapter.getItem(position);

        Intent intent = new Intent(DockerContainersActivity.this, DockerContainerActivity.class);
        intent.putExtra("id", dockerContainer.getId());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        dockerServiceFactory.getDockerService()
                .getContainers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<DockerContainer>>() {
                    @Override
                    public void call(List<DockerContainer> dockerContainers) {
                        dockerContainerAdapter.clear();
                        dockerContainerAdapter.addAll(dockerContainers);

                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.docker_container_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                // TODO: Remove this duplication
                dockerServiceFactory.getDockerService()
                        .getContainers()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<DockerContainer>>() {
                            @Override
                            public void call(List<DockerContainer> dockerContainers) {
                                dockerContainerAdapter.clear();
                                dockerContainerAdapter.addAll(dockerContainers);
                            }
                        });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
