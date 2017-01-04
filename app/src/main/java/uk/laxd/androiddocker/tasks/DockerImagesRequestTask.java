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
import uk.laxd.androiddocker.adapter.DockerImagesListAdapter;
import uk.laxd.androiddocker.dto.DockerImage;

/**
 * Created by lawrence on 04/01/17.
 */
public class DockerImagesRequestTask extends AsyncTask<Void, Void, List<DockerImage>> {

    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayAdapter arrayAdapter;

    private DockerService dockerService = new DockerServiceImpl();

    public DockerImagesRequestTask(Context context, SwipeRefreshLayout swipeRefreshLayout, ArrayAdapter arrayAdapter) {
        this.context = context;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.arrayAdapter = arrayAdapter;
    }

    @Override
    protected List<DockerImage> doInBackground(Void... voids) {
        Log.i(getClass().toString(), "Getting docker containers");

        List<DockerImage> dockerImages = new ArrayList<>();

        try {
            dockerImages = dockerService.getImages();
        } catch (Exception e) {
            Log.e(getClass().toString(), "Failed to get Docker Images", e);
            Toast.makeText(context, "Failed to reach docker API", Toast.LENGTH_SHORT)
                    .show();
        }

        return dockerImages;
    }

    @Override
    protected void onPostExecute(List<DockerImage> dockerImages) {
        arrayAdapter.clear();
        arrayAdapter.addAll(dockerImages);

        swipeRefreshLayout.setRefreshing(false);
    }
}
