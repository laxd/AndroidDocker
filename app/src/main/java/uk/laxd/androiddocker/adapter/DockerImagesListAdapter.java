package uk.laxd.androiddocker.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import uk.laxd.androiddocker.BR;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.dto.DockerImage;

/**
 * Created by lawrence on 04/01/17.
 */

public class DockerImagesListAdapter extends ViewHolderArrayAdapter<DockerImage> {
    public DockerImagesListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public DockerImagesListAdapter(Context context, int resource, List<DockerImage> objects) {
        super(context, resource, objects);
    }

    @Override
    public ViewHolder<DockerImage> createNewHolder(ViewGroup parent) {
        return new GenericViewHolder<>(getContext(), R.layout.docker_image_list_row, parent, BR.image);
    }
}
