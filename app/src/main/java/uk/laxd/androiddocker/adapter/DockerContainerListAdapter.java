package uk.laxd.androiddocker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;

        if(view == null) {
            LayoutInflater inf = LayoutInflater.from(getContext());
            view = inf.inflate(R.layout.docker_container_list_row, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        DockerContainer dockerContainer = getItem(position);

        if(dockerContainer != null) {
            holder.nameTextView.setText(dockerContainer.getName());
            holder.imageNameTextView.setText(dockerContainer.getImage());

            if("running".equals(dockerContainer.getState())) {
                holder.containerStatusView.setImageResource(android.R.drawable.presence_online);
            }
            else {
                holder.containerStatusView.setImageResource(android.R.drawable.presence_offline);
            }
        }

        return view;
    }

    private static class ViewHolder {
        @BindView(R.id.container_name)
        TextView nameTextView;

        @BindView(R.id.container_image)
        TextView imageNameTextView;

        @BindView(R.id.container_status)
        ImageView containerStatusView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
