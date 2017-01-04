package uk.laxd.androiddocker.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.adapter.DockerContainerListAdapter;
import uk.laxd.androiddocker.adapter.DockerImagesListAdapter;
import uk.laxd.androiddocker.dto.DockerContainer;
import uk.laxd.androiddocker.dto.DockerImage;
import uk.laxd.androiddocker.tasks.DockerContainersRequestTask;
import uk.laxd.androiddocker.tasks.DockerImagesRequestTask;

/**
 * Created by lawrence on 04/01/17.
 */

public class DockerImagesActivity extends ListActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                new DockerImagesRequestTask(DockerImagesActivity.this, swipeRefreshLayout, dockerImageAdapter).execute();
            }
        });

        new DockerImagesRequestTask(DockerImagesActivity.this, swipeRefreshLayout, dockerImageAdapter).execute();
    }
}
