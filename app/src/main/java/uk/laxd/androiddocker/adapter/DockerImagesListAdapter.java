package uk.laxd.androiddocker.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.laxd.androiddocker.BR;
import uk.laxd.androiddocker.R;
import uk.laxd.androiddocker.databinding.DockerImageListRowBinding;
import uk.laxd.androiddocker.dto.DockerImage;

/**
 * Created by lawrence on 04/01/17.
 */

public class DockerImagesListAdapter extends ViewHolderArrayAdapter<DockerImage, DockerImagesListAdapter.ViewHolder> {
    public DockerImagesListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public DockerImagesListAdapter(Context context, int resource, List<DockerImage> objects) {
        super(context, resource, objects);
    }

    @Override
    public ViewHolder createNewHolder(ViewGroup parent) {
        return new ViewHolder(parent);
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = createOrRestoreViewHolder(parent, view);

        DockerImage dockerImage = getItem(position);

        View boundView = holder.bindView(BR.image, dockerImage);

        boundView.setTag(holder);

        return boundView;
    }

    public class ViewHolder extends AbstractViewHolder<DockerImage> {
        public ViewHolder(ViewGroup parent) {
            super(getContext(), R.layout.docker_image_list_row, parent);
        }
    }
}
