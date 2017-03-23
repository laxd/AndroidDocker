package uk.laxd.androiddocker.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import uk.laxd.androiddocker.BR;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dto.DockerContainer;

/**
 * Created by lawrence on 04/01/17.
 */

public class DockerContainerListAdapter extends ViewHolderArrayAdapter<DockerContainer> {
    public DockerContainerListAdapter(Context context, int resource, List<DockerContainer> objects) {
        super(context, resource, objects);
    }

    @Override
    public ViewHolder<DockerContainer> createNewHolder(ViewGroup parent) {
        return new GenericViewHolder<>(getContext(), R.layout.docker_container_list_row, parent, BR.container);
    }
}
