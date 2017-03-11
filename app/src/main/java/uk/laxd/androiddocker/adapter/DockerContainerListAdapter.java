package uk.laxd.androiddocker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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

public class DockerContainerListAdapter extends ViewHolderArrayAdapter<DockerContainer, DockerContainerListAdapter.ViewHolder> {
    public DockerContainerListAdapter(Context context, int resource, List<DockerContainer> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = createOrRestoreViewHolder(view, R.layout.docker_container_list_row);

        DockerContainer dockerContainer = getItem(position);

        if(dockerContainer != null) {
            holder.nameTextView.setText(dockerContainer.getNames()[0].substring(1));
            holder.imageNameTextView.setText(dockerContainer.getImage());
            holder.containerStatusView.setBackgroundResource(dockerContainer.getState().getImageResource());
            holder.containerStatusView.setText(dockerContainer.getState().name().toUpperCase());
        }

        return holder.getBaseView();
    }

    @Override
    public ViewHolder createNewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbstractViewHolder {
        @BindView(R.id.container_name)
        TextView nameTextView;

        @BindView(R.id.container_image)
        TextView imageNameTextView;

        @BindView(R.id.container_status)
        TextView containerStatusView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
