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

/**
 * Created by lawrence on 04/01/17.
 */

public class DockerContainerListAdapter extends ArrayAdapter<DockerContainer> {
    public DockerContainerListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public DockerContainerListAdapter(Context context, int resource, List<DockerContainer> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null) {
            LayoutInflater inf = LayoutInflater.from(getContext());
            v = inf.inflate(R.layout.docker_container_list_row, null);
        }

        DockerContainer dockerContainer = getItem(position);

        if(dockerContainer != null) {
            TextView nameTextview = (TextView) v.findViewById(R.id.container_name);

            if(nameTextview != null) {
                nameTextview.setText(TextUtils.join(",", dockerContainer.getNames()));
            }
        }

        return v;
    }
}
