package uk.laxd.androiddocker.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uk.laxd.androiddocker.DockerService;
import uk.laxd.androiddocker.DockerServiceImpl;
import uk.laxd.androiddocker.dto.DockerContainer;

/**
 * Created by lawrence on 03/01/17.
 */

public class DockerContainersRequestTask extends AsyncTask<Object, Object, List<DockerContainer>> {

    private DockerService dockerService = new DockerServiceImpl();

    private Context context;
    private ArrayAdapter<DockerContainer> adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public DockerContainersRequestTask(Context context, SwipeRefreshLayout swipeRefreshLayout, ArrayAdapter<DockerContainer> adapter) {
        this.context = context;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.adapter = adapter;
    }

    @Override
    protected List<DockerContainer> doInBackground(Object... voids) {
        Log.i(getClass().toString(), "Getting docker containers");

        List<DockerContainer> dockerContainers = new ArrayList<>();

        try {
            dockerContainers = dockerService.getContainers();
        } catch (Exception e) {
            Log.e(getClass().toString(), "Failed to get Docker Containers", e);
            Toast.makeText(context, "Failed to reach docker API", Toast.LENGTH_SHORT)
                    .show();
        }

        return dockerContainers;
    }

    @Override
    protected void onPostExecute(List<DockerContainer> dockerContainers) {
        adapter.clear();
        adapter.addAll(dockerContainers);

        swipeRefreshLayout.setRefreshing(false);
    }
}
