package uk.laxd.androiddocker.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import uk.laxd.androiddocker.BR;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.databinding.DockerContainerListRowBinding;
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
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        ButterKnife.bind(this, view);

        ViewHolder holder = createOrRestoreViewHolder(parent, view);

        DockerContainer dockerContainer = getItem(position);

        View boundView = holder.bindView(BR.container, dockerContainer);

        boundView.setTag(holder);

        return boundView;
    }

    @Override
    public ViewHolder createNewHolder(ViewGroup parent) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends AbstractViewHolder<DockerContainer> {
        public ViewHolder(ViewGroup parent) {
            super(getContext(), R.layout.docker_container_list_row, parent);
        }
    }
}
