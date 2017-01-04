package uk.laxd.androiddocker.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.adapter.DockerContainerListAdapter;
import uk.laxd.androiddocker.dto.DockerContainer;
import uk.laxd.androiddocker.tasks.DockerContainersRequestTask;

/**
 * Created by lawrence on 04/01/17.
 */
public class DockerContainersActivity extends ListActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                new DockerContainersRequestTask(DockerContainersActivity.this, swipeRefreshLayout, dockerContainerAdapter).execute();
            }
        });

        new DockerContainersRequestTask(DockerContainersActivity.this, swipeRefreshLayout, dockerContainerAdapter).execute();
    }

}
