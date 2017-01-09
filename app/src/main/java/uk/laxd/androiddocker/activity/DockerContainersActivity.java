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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
public class DockerContainersActivity extends AppCompatActivity {

    private DockerContainerListAdapter dockerContainerAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.container_list);

        dockerContainerAdapter = new DockerContainerListAdapter(this, R.layout.docker_container_list_row, new ArrayList<DockerContainer>());
        listView.setAdapter(dockerContainerAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                DockerContainer dockerContainer = (DockerContainer) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(DockerContainersActivity.this, DockerContainerActivity.class);
                intent.putExtra("id", dockerContainer.getId());
                startActivity(intent);
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
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

        // TODO: Remove this duplication
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
