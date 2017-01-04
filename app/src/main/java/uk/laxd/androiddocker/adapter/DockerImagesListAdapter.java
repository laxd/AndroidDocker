package uk.laxd.androiddocker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dto.DockerContainer;
import uk.laxd.androiddocker.dto.DockerImage;

/**
 * Created by lawrence on 04/01/17.
 */

public class DockerImagesListAdapter extends ArrayAdapter<DockerImage> {
    public DockerImagesListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public DockerImagesListAdapter(Context context, int resource, List<DockerImage> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null) {
            LayoutInflater inf = LayoutInflater.from(getContext());
            v = inf.inflate(R.layout.docker_image_list_row, null);
        }

        DockerImage dockerImage = getItem(position);

        if(dockerImage != null) {
            TextView nameTextview = (TextView) v.findViewById(R.id.image_name);

            if(nameTextview != null) {
                nameTextview.setText(TextUtils.join(",", dockerImage.getRepoTags()));
            }
        }

        return v;
    }
}
